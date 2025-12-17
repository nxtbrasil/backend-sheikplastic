package com.sheikplastic.dto;

import java.math.BigDecimal;

public interface PessoaProdutoProjection {
    
    // Nomes dos métodos DEVE ser os mesmos dos aliases da query (AS seqProduto, AS nomeProduto, etc.)
    Long getSeqProduto();
    String getNomeProduto();
    String getComplementoProduto();
    String getUnpProduto();
    String getUnvProduto();
    BigDecimal getValorVenda(); 
    Long getQtdItens();
}