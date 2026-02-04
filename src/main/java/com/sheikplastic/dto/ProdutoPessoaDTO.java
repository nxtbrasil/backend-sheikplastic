package com.sheikplastic.dto;

import java.math.BigDecimal;
    
import lombok.Data;

@Data
public class ProdutoPessoaDTO {

    private Long seqProduto;
    private String nomeProduto;
    private String complementoProduto;
    private BigDecimal valorVenda;
    private String unpProduto;
    private String unvProduto;
}
