package com.sheikplastic.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PedidoItemDTO {

    private Long idPedido;
    private Long idPessoa;
    private Long seqProduto;

    private BigDecimal valorVenda;
    private BigDecimal qtdItem;
    private String unpItem;

    private Long idProduto;
    private String nomeProduto;
    private String complementoProduto;

    private BigDecimal qtdEntregue;
    private String unvItem;

    public PedidoItemDTO(
        Long idPedido,
        Long idPessoa,
        Long seqProduto,
        BigDecimal valorVenda,
        BigDecimal qtdItem,
        String unpItem,
        Long idProduto,
        String nomeProduto,
        String complementoProduto,
        BigDecimal qtdEntregue,
        String unvItem
    ) {
        this.idPedido = idPedido;
        this.idPessoa = idPessoa;
        this.seqProduto = seqProduto;
        this.valorVenda = valorVenda;
        this.qtdItem = qtdItem;
        this.unpItem = unpItem;
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.complementoProduto = complementoProduto;
        this.qtdEntregue = qtdEntregue;
        this.unvItem = unvItem;
    }
}
