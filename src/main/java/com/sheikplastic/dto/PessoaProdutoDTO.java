package com.sheikplastic.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PessoaProdutoDTO {

    private Long seqProduto;
    private String nomeProduto;
    private String complementoProduto;
    private String unpProduto;
    private String unvProduto;
    private BigDecimal valorVenda;
    private Long qtdItens;

    /* GETTERS E SETTERS */
}
