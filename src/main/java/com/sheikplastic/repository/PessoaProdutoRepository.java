package com.sheikplastic.repository;

import com.sheikplastic.dto.PessoaProdutoDTO;
import com.sheikplastic.dto.PessoaProdutoListaDTO;
import com.sheikplastic.model.PessoaProduto;
import com.sheikplastic.model.PessoaProdutoId;
import com.sheikplastic.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PessoaProdutoRepository
                extends JpaRepository<PessoaProduto, PessoaProdutoId> {

        /*
         * =========================================================
         * BÁSICO
         * =========================================================
         */

        List<PessoaProduto> findByIdPessoa(Long idPessoa);

        /*
         * =========================================================
         * 🔥 PRECIFICAÇÃO EM LOTE (IGUAL AO ASP CLÁSSICO)
         * WHERE idPessoa + idProduto → retorna VÁRIOS seqProduto
         * =========================================================
         */
        @Query("""
                            SELECT pp
                            FROM PessoaProduto pp
                            WHERE pp.idPessoa = :idPessoa
                              AND pp.idProduto = :idProduto
                        """)
        List<PessoaProduto> buscarParaPrecificacao(
                        @Param("idPessoa") Long idPessoa,
                        @Param("idProduto") Integer idProduto);

        /*
         * =========================================================
         * LISTAGEM DA TELA (DTO) – IGUAL AO ASP
         * =========================================================
         */
        @Query(value = """
                            SELECT
                                ppr.seqProduto            AS seqProduto,
                                prd.nomeProduto           AS nomeProduto,
                                ppr.complementoProduto    AS complementoProduto,
                                ppr.unpProduto            AS unpProduto,
                                ppr.unvProduto            AS unvProduto,
                                ppr.valorVenda            AS valorVenda,
                                prd.idProduto             AS idProduto,
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
                        """, nativeQuery = true)
        List<PessoaProdutoDTO> findProdutosDaPessoa(
                        @Param("idPessoa") Long idPessoa);

        /*
         * =========================================================
         * DETALHE DE UM PRODUTO (POR seqProduto)
         * =========================================================
         */
        @Query(value = """
                            SELECT
                                ppr.seqProduto         AS seqProduto,
                                ppr.idProduto          AS idProduto,
                                prd.nomeProduto        AS nomeProduto,
                                ppr.complementoProduto AS complementoProduto,
                                ppr.unpProduto         AS unpProduto,
                                ppr.unvProduto         AS unvProduto,
                                ppr.valorVenda         AS valorVenda,
                                ppr.idPessoa           AS idPessoa
                            FROM PessoaProduto ppr
                            INNER JOIN Produto prd
                                ON prd.idProduto = ppr.idProduto
                            WHERE ppr.idProduto = :idProduto
                              AND ppr.seqProduto = :seqProduto
                              AND ppr.idPessoa = :idPessoa
                        """, nativeQuery = true)
        Optional<PessoaProdutoDTO> buscarDetalheProduto(
                        @Param("idProduto") Long idProduto,
                        @Param("seqProduto") Long seqProduto,
                        @Param("idPessoa") Long idPessoa);

        /*
         * =========================================================
         * 🔢 PRÓXIMO SEQ (ASP: MAX + 1)
         * =========================================================
         */
        @Query(value = """
                            SELECT COALESCE(MAX(seqProduto), 0) + 1
                            FROM PessoaProduto
                            WHERE idPessoa = :idPessoa
                        """, nativeQuery = true)
        Long proximoSeqProduto(@Param("idPessoa") Long idPessoa);

        /*
         * =========================================================
         * INSERT (IGUAL AO ASP)
         * =========================================================
         */
        @Modifying
        @Query(value = """
                            INSERT INTO PessoaProduto
                            (idPessoa, seqProduto, idProduto, complementoProduto, unpProduto, unvProduto, valorVenda)
                            VALUES
                            (:idPessoa, :seqProduto, :idProduto, :complemento, :unp, :unv, :valorVenda)
                        """, nativeQuery = true)
        void inserirProduto(
                        @Param("idPessoa") Long idPessoa,
                        @Param("seqProduto") Long seqProduto,
                        @Param("idProduto") Integer idProduto,
                        @Param("complemento") String complemento,
                        @Param("unp") String unp,
                        @Param("unv") String unv,
                        @Param("valorVenda") BigDecimal valorVenda);

        /*
         * =========================================================
         * UPDATE
         * =========================================================
         */
        @Modifying
        @Query(value = """
                            UPDATE PessoaProduto
                            SET idProduto = :idProduto,
                                complementoProduto = :complemento,
                                unpProduto = :unp,
                                unvProduto = :unv,
                                valorVenda = :valorVenda
                            WHERE idPessoa = :idPessoa
                              AND seqProduto = :seqProduto
                        """, nativeQuery = true)
        void atualizarProduto(
                        @Param("idPessoa") Long idPessoa,
                        @Param("seqProduto") Long seqProduto,
                        @Param("idProduto") Integer idProduto,
                        @Param("complemento") String complemento,
                        @Param("unp") String unp,
                        @Param("unv") String unv,
                        @Param("valorVenda") BigDecimal valorVenda);

        /*
         * =========================================================
         * DELETE
         * =========================================================
         */
        @Modifying
        @Query(value = """
                            DELETE FROM PessoaProduto
                            WHERE idPessoa = :idPessoa
                              AND seqProduto = :seqProduto
                        """, nativeQuery = true)
        void deletarProduto(
                        @Param("idPessoa") Long idPessoa,
                        @Param("seqProduto") Long seqProduto);

        @Query(value = """
                            SELECT
                                ppr.seqProduto           AS seqProduto,
                                prd.nomeProduto          AS nomeProduto,
                                ppr.complementoProduto   AS complementoProduto,
                                ppr.valorVenda           AS valorVenda
                            FROM PessoaProduto ppr
                            INNER JOIN Produto prd ON prd.idProduto = ppr.idProduto
                            WHERE ppr.idPessoa = :idPessoa
                              AND ppr.idProduto = :idProduto
                            ORDER BY prd.nomeProduto, ppr.complementoProduto
                        """, nativeQuery = true)
        List<PessoaProdutoListaDTO> listarPorPessoaEProduto(
                        @Param("idPessoa") Long idPessoa,
                        @Param("idProduto") Long idProduto);

        @Query("SELECT p FROM Produto p ORDER BY p.nomeProduto ASC")
        List<Produto> listarProdutosParaCombo();

}
