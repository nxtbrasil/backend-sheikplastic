package com.sheikplastic.repository;

import com.sheikplastic.dto.HistoricoPrecoDTO;
import com.sheikplastic.model.HistoricoPreco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface HistoricoPrecoRepository extends JpaRepository<HistoricoPreco, Long> {

    @Query(
        value = """
            SELECT 
                valorVenda AS valorVenda,
                dtValidade AS dtValidade
            FROM HistoricoPreco
            WHERE idPessoa = :idPessoa
              AND seqProduto = :seqProduto
            ORDER BY 
                CASE 
                    WHEN dtValidade IS NULL THEN GETDATE()
                    ELSE dtValidade
                END DESC
        """,
        nativeQuery = true
    )
    List<HistoricoPrecoDTO> buscarHistorico(
        @Param("idPessoa") Long idPessoa,
        @Param("seqProduto") Long seqProduto
    );

    // 🔒 FECHA O PREÇO ANTERIOR (ASP: UPDATE HistoricoPreco SET dtValidade = Now())
    @Modifying
    @Query(value = """
        UPDATE HistoricoPreco
        SET dtValidade = CURRENT_TIMESTAMP
        WHERE idPessoa = :idPessoa
          AND seqProduto = :seqProduto
          AND valorVenda = :valorVendaAnterior
    """, nativeQuery = true)
    void fecharPrecoAnterior(
        @Param("idPessoa") Long idPessoa,
        @Param("seqProduto") Long seqProduto,
        @Param("valorVendaAnterior") BigDecimal valorVendaAnterior
    );

    // ➕ INSERE NOVO HISTÓRICO
    @Modifying
    @Query(value = """
        INSERT INTO HistoricoPreco (idPessoa, seqProduto, valorVenda)
        VALUES (:idPessoa, :seqProduto, :valorVenda)
    """, nativeQuery = true)
    void inserirHistorico(
        @Param("idPessoa") Long idPessoa,
        @Param("seqProduto") Long seqProduto,
        @Param("valorVenda") BigDecimal valorVenda
    );
}
