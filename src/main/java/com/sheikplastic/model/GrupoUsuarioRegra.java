package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GrupoUsuarioRegra")
@Data
@NoArgsConstructor
public class GrupoUsuarioRegra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // se não tiver na tabela, cria artificialmente

    @ManyToOne
    @JoinColumn(name = "idGrupoUsuario")
    private GrupoUsuario grupoUsuario;

    @ManyToOne
    @JoinColumn(name = "idRegra")
    private Regra regra;
}
