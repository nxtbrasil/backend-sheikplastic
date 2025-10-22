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
    private Long id;
    private Integer idGrupoUsuario;
    private Integer idRegra;
}
