package com.sheikplastic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sheikplastic.model.PessoaProduto;
import com.sheikplastic.model.PessoaProdutoId;

import java.util.List;

@Repository
public interface PessoaProdutoRepository
        extends JpaRepository<PessoaProduto, PessoaProdutoId> {

    List<PessoaProduto> findByIdPessoa(Long idPessoa);

    @Query("""
        SELECT COUNT(pi)
        FROM PedidoItem pi
        WHERE pi.id.idPessoa = :idPessoa
          AND pi.id.seqProduto = :seqProduto
    """)
    Long countItens(
        @Param("idPessoa") Long idPessoa,
        @Param("seqProduto") Long seqProduto
    );
}