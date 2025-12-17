package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "HistoricoPreco")
public class HistoricoPreco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistoricoPreco;

    @Column(nullable = false)
    private Long idPessoa;

    @Column(nullable = false)
    private Long seqProduto;

    @Column(nullable = false)
    private BigDecimal valorVenda;

    @Column
    private LocalDate dtValidade;

    // getters e setters
}
