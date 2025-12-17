package com.sheikplastic.dto;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class PessoaProdutoRequest {

    private Long idPessoa;
    private Long seqProduto;
    private Long idProduto;

    private String complementoProduto;
    private String unpProduto;
    private String unvProduto;

    private BigDecimal valorVenda;
    private BigDecimal valorVendaAnterior;
}
