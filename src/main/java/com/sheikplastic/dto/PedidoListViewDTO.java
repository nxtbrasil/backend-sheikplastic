package com.sheikplastic.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sheikplastic.model.StatusPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoListViewDTO {

    private Long idPedido;
    private String nomePessoa;
    private Long idPessoa;
    private String nomeFuncionario;
    private LocalDate dtEntradaPedido;
    private LocalDate dtPrevisaoPedido;
    private LocalDate dtEntregaPedido;

    private Boolean urgente;
    private Boolean entregue;
    private Boolean ativo;

    private Long qtdItens;
    private Long qtdEntregue;
    private BigDecimal valorPedido;

    private StatusPedido status;
    private Integer temItensEntregues;

    public PedidoListViewDTO(PedidoListDTO p) {
        this.idPedido = p.getIdPedido();
        this.nomePessoa = p.getNomePessoa();
        this.idPessoa = p.getIdPessoa();
        this.nomeFuncionario = p.getNomeFuncionario();
        this.dtEntradaPedido = p.getDtEntradaPedido();
        this.dtPrevisaoPedido = p.getDtPrevisaoPedido();
        this.dtEntregaPedido = p.getDtEntregaPedido();
        this.urgente = p.getUrgente();
        this.entregue = p.getEntregue();
        this.ativo = p.getAtivo();
        this.qtdItens = p.getQtdItens();
        this.qtdEntregue = p.getQtdEntregue();
        this.valorPedido = p.getValorPedido();
        this.temItensEntregues = p.getTemItensEntregues();

        this.status = calcularStatus(p);
    }

    private StatusPedido calcularStatus(PedidoListDTO p) {

        if (p.getQtdItens() == null || p.getQtdItens() == 0) {
            return StatusPedido.NOVO;
        }

        if (Boolean.TRUE.equals(p.getEntregue())) {
            return StatusPedido.ENTREGUE;
        }

        if (p.getDtPrevisaoPedido() != null &&
            !p.getDtPrevisaoPedido().isBefore(LocalDate.now())) {
            return StatusPedido.EM_ANDAMENTO;
        }

        return StatusPedido.ATRASADO;
    }

    // getters
}
