package com.sheikplastic.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.sheikplastic.model.CondicaoPagamento;
import com.sheikplastic.model.StatusPedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PedidoImpressaoDTO {

  private Long idPedido;
  private Long idPessoa;

  private String nomeCliente;
  private String nomeFuncionario;
  private String descricaoCondicaoPagamento;

  private LocalDate dtEntradaPedido;
  private LocalDate dtPrevisaoPedido;
  private LocalDate dtEntregaPedido;

  private Boolean urgente;
  private String observacao;  



  private Boolean entregue;
  private BigDecimal qtdTotal;
  private BigDecimal valorTotal;
  private BigDecimal valorEntregue;

  private StatusPedido statusPedido;
  
  private List<PedidoImpressaoContatoDTO> contatos;

  private PedidoImpressaoEnderecoDTO endereco;

  private CondicaoPagamentoDTO condicaoPagamento;
  

  public PedidoImpressaoDTO(
      Long idPedido,
      Long idPessoa,
      String nomeCliente,
      String nomeFuncionario,
      String descricaoCondicaoPagamento,
      LocalDate dtEntradaPedido,
      LocalDate dtPrevisaoPedido,
      LocalDate dtEntregaPedido,
      Boolean urgente,
      Boolean entregue,
      String observacao,
      BigDecimal qtdTotal,
      BigDecimal valorTotal,
      BigDecimal valorEntregue) {
    this.idPedido = idPedido;
    this.idPessoa = idPessoa;
    this.nomeCliente = nomeCliente;
    this.nomeFuncionario = nomeFuncionario;
    this.descricaoCondicaoPagamento = descricaoCondicaoPagamento;
    this.dtEntradaPedido = dtEntradaPedido;
    this.dtPrevisaoPedido = dtPrevisaoPedido;
    this.dtEntregaPedido = dtEntregaPedido;
    this.urgente = urgente;
    this.entregue = entregue;
    this.observacao = observacao;
    this.qtdTotal = qtdTotal;
    this.valorTotal = valorTotal;
    this.valorEntregue = valorEntregue;

  }

}
