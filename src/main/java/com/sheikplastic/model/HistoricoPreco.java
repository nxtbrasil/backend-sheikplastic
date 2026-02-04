package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HistoricoPreco")
@Data
public class HistoricoPreco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer idPessoa;
    private Integer seqProduto;

    private BigDecimal valorVenda;

    private LocalDateTime dtValidade;

    // getters e setters
}