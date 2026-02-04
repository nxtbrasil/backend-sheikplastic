package com.sheikplastic.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    
    private Long idPedido;

    @Column(name = "idPessoa")
    private Long idPessoa;

    @Column(name = "idFuncionario")
    private Long idFuncionario;

    @Column(name = "idCondicaoPagamento")
    private Long idCondicaoPagamento;

    private LocalDate dtEntradaPedido;
    private LocalDate dtPrevisaoPedido;
    private LocalDate dtEntregaPedido;

    private String nomeSolicitante;
    private Boolean urgente;
    private String observacao;
    private Boolean entregue;
    private Boolean ativo;
}
