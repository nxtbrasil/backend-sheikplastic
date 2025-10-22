package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GrupoUsuarioHeranca")
@Data
@NoArgsConstructor
public class GrupoUsuarioHeranca {

    @EmbeddedId
    private GrupoUsuarioHerancaId id;

    @MapsId("idGrupoPai")
    @ManyToOne
    @JoinColumn(name = "idGrupoPai")
    private GrupoUsuario grupoPai;

    @MapsId("idGrupoFilho")
    @ManyToOne
    @JoinColumn(name = "idGrupoFilho")
    private GrupoUsuario grupoFilho;
}
