package com.sheikplastic.repository;

import com.sheikplastic.dto.PessoaProdutoDTO;
import com.sheikplastic.model.PessoaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PessoaProdutoRepository extends JpaRepository<PessoaProduto, Long> {

    // LISTAGEM
    @Query(
        value = """
            SELECT 
                ppr.seqProduto AS seqProduto,
                prd.nomeProduto AS nomeProduto,
                ppr.complementoProduto AS complementoProduto,
                ppr.unpProduto AS unpProduto,
                ppr.unvProduto AS unvProduto,
                ppr.valorVenda AS valorVenda,
                (
                    SELECT COUNT(*)
                    FROM PedidoItem pit
                    WHERE pit.idPessoa = ppr.idPessoa
                      AND pit.seqProduto = ppr.seqProduto
                ) AS qtdItens
            FROM PessoaProduto ppr
            INNER JOIN Produto prd ON prd.idProduto = ppr.idProduto
            WHERE ppr.idPessoa = :idPessoa
            ORDER BY prd.nomeProduto, ppr.complementoProduto
        """,
        nativeQuery = true
    )
    List<PessoaProdutoDTO> findProdutosDaPessoa(@Param("idPessoa") Long idPessoa);

    // 🔥 PRÓXIMO SEQ (ASP: MAX + 1)
    @Query(
        value = """
            SELECT COALESCE(MAX(seqProduto), 0) + 1
            FROM PessoaProduto
            WHERE idPessoa = :idPessoa
        """,
        nativeQuery = true
    )
    Long proximoSeqProduto(@Param("idPessoa") Long idPessoa);

    // INSERT
    @Modifying
    @Query(
        value = """
            INSERT INTO PessoaProduto
            (idPessoa, seqProduto, idProduto, complementoProduto, unpProduto, unvProduto, valorVenda)
            VALUES
            (:idPessoa, :seqProduto, :idProduto, :complemento, :unp, :unv, :valorVenda)
        """,
        nativeQuery = true
    )
    void inserirProduto(
        @Param("idPessoa") Long idPessoa,
        @Param("seqProduto") Long seqProduto,
        @Param("idProduto") Long idProduto,
        @Param("complemento") String complemento,
        @Param("unp") String unp,
        @Param("unv") String unv,
        @Param("valorVenda") BigDecimal valorVenda
    );

    // UPDATE
    @Modifying
    @Query(
        value = """
            UPDATE PessoaProduto
            SET idProduto = :idProduto,
                complementoProduto = :complemento,
                unpProduto = :unp,
                unvProduto = :unv,
                valorVenda = :valorVenda
            WHERE idPessoa = :idPessoa
              AND seqProduto = :seqProduto
        """,
        nativeQuery = true
    )
    void atualizarProduto(
        @Param("idPessoa") Long idPessoa,
        @Param("seqProduto") Long seqProduto,
        @Param("idProduto") Long idProduto,
        @Param("complemento") String complemento,
        @Param("unp") String unp,
        @Param("unv") String unv,
        @Param("valorVenda") BigDecimal valorVenda
    );

    // DELETE (somente quando qtdItens = 0 → regra já validada no front)
    @Modifying
    @Query(
        value = """
            DELETE FROM PessoaProduto
            WHERE idPessoa = :idPessoa
              AND seqProduto = :seqProduto
        """,
        nativeQuery = true
    )
    void deletarProduto(
        @Param("idPessoa") Long idPessoa,
        @Param("seqProduto") Long seqProduto
    );


     @Query(value = """
        SELECT 
            ppr.seqProduto            AS seqProduto,
            ppr.idProduto             AS idProduto,
            prd.nomeProduto           AS nomeProduto,
            ppr.complementoProduto    AS complementoProduto,
            ppr.unpProduto            AS unpProduto,
            ppr.unvProduto            AS unvProduto,
            ppr.valorVenda            AS valorVenda,
            ppr.idProduto as idProduto,
            (
                SELECT COUNT(*)
                FROM PedidoItem pit
                WHERE pit.idPessoa = ppr.idPessoa
                  AND pit.seqProduto = ppr.seqProduto
            ) AS qtdItens
        FROM PessoaProduto ppr
        INNER JOIN Produto prd ON prd.idProduto = ppr.idProduto
        WHERE ppr.idPessoa = :idPessoa
          AND ppr.seqProduto = :seqProduto
    """, nativeQuery = true)
    Optional<PessoaProdutoDTO> buscarDetalheProduto(
        @Param("idPessoa") Long idPessoa,
        @Param("seqProduto") Long seqProduto
    );
}
