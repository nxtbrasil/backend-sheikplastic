package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CondicaoPagamento")
@Data
public class CondicaoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCondicaoPagamento")
    private Integer idCondicaoPagamento;

    @Column(name = "descricaoCondicaoPagamento", nullable = false, length = 100)
    private String descricaoCondicaoPagamento;
}
