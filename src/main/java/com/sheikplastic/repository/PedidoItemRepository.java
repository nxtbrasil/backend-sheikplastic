package com.sheikplastic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sheikplastic.dto.PedidoItemDTO;
import com.sheikplastic.dto.PedidoItemView;
import com.sheikplastic.model.PedidoItem;

import jakarta.transaction.Transactional;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {


    

    @Query(value = """
    SELECT 
        prd.nomeProduto       AS nomeProduto,
        ppr.idPessoa          AS idPessoa,
        ppr.seqProduto        AS seqProduto,
        ppr.complementoProduto AS complementoProduto,
        pit.valorVenda        AS valorVenda,
        pit.qtdItem           AS qtdItem,
        pit.unpItem           AS unpItem,
        pit.qtdEntregue       AS qtdEntregue,
        pit.unvItem           AS unvItem
    FROM PedidoItem pit
    INNER JOIN PessoaProduto ppr 
        ON pit.idPessoa = ppr.idPessoa 
       AND pit.seqProduto = ppr.seqProduto
    INNER JOIN Produto prd 
        ON ppr.idProduto = prd.idProduto
    WHERE pit.idPedido = :idPedido
    ORDER BY pit.seqProduto
""", nativeQuery = true)
List<PedidoItemView> listarItensPedido(@Param("idPedido") Long idPedido);


    Optional<PedidoItem> findByIdPedidoAndIdPessoaAndSeqProduto(
        Long idPedido,
        Long idPessoa,
        Long seqProduto
    );

    boolean existsByIdPedidoAndIdPessoaAndSeqProduto(
        Long idPedido,
        Long idPessoa,
        Long seqProduto
    );

        List<PedidoItem> findByIdPedido(Long idPedido);


 @Query("""
    SELECT new com.sheikplastic.dto.PedidoItemDTO(
        pi.idPedido,
        pi.idPessoa,
        pi.seqProduto,
        pi.valorVenda,
        pi.qtdItem,
        pi.unpItem,
        p.idProduto,
        p.nomeProduto,
        pp.complementoProduto,
        pi.qtdEntregue,
        pi.unvItem
    )
    FROM PedidoItem pi
    LEFT JOIN PessoaProduto pp
        ON pp.seqProduto = pi.seqProduto
       AND pp.idPessoa = pi.idPessoa
    JOIN Produto p
        ON p.idProduto = pp.idProduto
    WHERE pi.idPedido = :idPedido
    ORDER BY pi.seqProduto
""")
List<PedidoItemDTO> listarItensImpressao(@Param("idPedido") Long idPedido);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM PedidoItem pi
        WHERE pi.idPedido IN :idsPedido
    """)
    void deletarPorPedidos(List<Long> idsPedido);

}
