package com.sheikplastic.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PedidoListDTO {

    Long getIdPedido();
    String getNomePessoa();
    Long getIdPessoa();

    LocalDate getDtEntradaPedido();
    LocalDate getDtPrevisaoPedido();
    LocalDate getDtEntregaPedido();

    String getNomeFuncionario();

    Boolean getUrgente();
    Boolean getEntregue();
    Boolean getAtivo();

    Long getQtdItens();
    Long getQtdEntregue();
    BigDecimal getValorPedido();
    Integer getTemItensEntregues();
}
