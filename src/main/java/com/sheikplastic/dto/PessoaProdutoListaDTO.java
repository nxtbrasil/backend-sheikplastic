package com.sheikplastic.dto;

import java.math.BigDecimal;

public interface PessoaProdutoListaDTO {

    Long getSeqProduto();
    String getNomeProduto();
    String getComplementoProduto();
    BigDecimal getValorVenda();
}
