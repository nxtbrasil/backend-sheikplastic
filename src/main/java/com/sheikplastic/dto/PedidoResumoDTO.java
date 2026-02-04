package com.sheikplastic.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PedidoResumoDTO {

    Long getIdPessoa();
    String getNomePessoa();

    LocalDate getDtEntradaPedido();
    LocalDate getDtPrevisaoPedido();
    LocalDate getDtEntregaPedido();

    String getNomeFuncionario();
    String getDescricaoCondicaoPagamento();

    Boolean getUrgente();
    Boolean getEntregue();

    BigDecimal getQtdItens();
    BigDecimal getValorPedido();
    BigDecimal getValorEntregue();
}
