package com.sheikplastic.dto;

import java.math.BigDecimal;

public interface PedidoItemView {

    String getNomeProduto();
    Long getIdPessoa();
    Long getSeqProduto();
    String getComplementoProduto();

    BigDecimal getValorVenda();
    BigDecimal getQtdItem();
    String getUnpItem();

    BigDecimal getQtdEntregue();
    String getUnvItem();
}

