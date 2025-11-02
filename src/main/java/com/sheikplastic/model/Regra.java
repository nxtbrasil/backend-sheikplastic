package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Regra")
@Data
@NoArgsConstructor
public class Regra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegra;

    @Column(length = 8, nullable = false)
    private String chaveRegra;

    @Column(length = 100, nullable = false)
    private String descricaoRegra;
}
