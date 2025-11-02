package com.sheikplastic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GrupoUsuarioHeranca")
public class GrupoUsuarioHeranca {

    @EmbeddedId
    private GrupoUsuarioHerancaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idGrupoPai") // vincula com o campo do ID composto
    @JoinColumn(name = "idGrupoPai", referencedColumnName = "idGrupoUsuario")
    private GrupoUsuario grupoPai;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idGrupoFilho") // vincula com o campo do ID composto
    @JoinColumn(name = "idGrupoFilho", referencedColumnName = "idGrupoUsuario")
    private GrupoUsuario grupoFilho;

    public GrupoUsuarioHerancaId getId() {
        return id;
    }

    public void setId(GrupoUsuarioHerancaId id) {
        this.id = id;
    }

    public GrupoUsuario getGrupoPai() {
        return grupoPai;
    }

    public void setGrupoPai(GrupoUsuario grupoPai) {
        this.grupoPai = grupoPai;
    }

    public GrupoUsuario getGrupoFilho() {
        return grupoFilho;
    }

    public void setGrupoFilho(GrupoUsuario grupoFilho) {
        this.grupoFilho = grupoFilho;
    }
}
