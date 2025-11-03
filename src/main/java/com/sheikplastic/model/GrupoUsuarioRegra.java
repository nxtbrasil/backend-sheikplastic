package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GrupoUsuarioRegra")
@Data
@NoArgsConstructor
public class GrupoUsuarioRegra {

    @EmbeddedId
    private GrupoUsuarioRegraId id = new GrupoUsuarioRegraId();

    @ManyToOne
    @MapsId("idGrupoUsuario")
    @JoinColumn(name = "idGrupoUsuario")
    private GrupoUsuario grupoUsuario;

    @ManyToOne
    @MapsId("idRegra")
    @JoinColumn(name = "idRegra")
    private Regra regra;
}
