package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TipoContato")
public class TipoContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoContato")
    private Long id;

    @Column(name = "descricaoTipoContato")
    private String descricao;
}
