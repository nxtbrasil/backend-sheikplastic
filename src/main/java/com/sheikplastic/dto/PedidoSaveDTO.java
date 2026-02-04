package com.sheikplastic.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PedidoSaveDTO {

    private Long idPedido;
    private Long idPessoa;
    private Long idFuncionario;
    private Long idCondicaoPagamento;

    private LocalDate dtEntradaPedido;
    private LocalDate dtPrevisaoPedido;
    private LocalDate dtEntregaPedido;

    private String nomeSolicitante;
    private Boolean urgente;
    private String observacao;
    private Boolean entregue;
    private Boolean ativo;

    // getters e setters
}
