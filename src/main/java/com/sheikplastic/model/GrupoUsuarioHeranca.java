package com.sheikplastic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GrupoUsuarioHeranca")
public class GrupoUsuarioHeranca {

    @EmbeddedId
    private GrupoUsuarioHerancaId id;

    @ManyToOne
    @MapsId("idGrupoPai")
    @JoinColumn(name = "idGrupoPai")
    private GrupoUsuario grupoPai;

    @ManyToOne
    @MapsId("idGrupoFilho")
    @JoinColumn(name = "idGrupoFilho")
    private GrupoUsuario grupoFilho;

    public GrupoUsuarioHeranca() {}

    // 🔹 Construtor usado quando você já tem os objetos de grupo
    public GrupoUsuarioHeranca(GrupoUsuario grupoPai, GrupoUsuario grupoFilho) {
        this.grupoPai = grupoPai;
        this.grupoFilho = grupoFilho;
        this.id = new GrupoUsuarioHerancaId(grupoPai.getIdGrupoUsuario(), grupoFilho.getIdGrupoUsuario());
    }

    // 🔹 Construtor adicional para aceitar diretamente o ID composto
    public GrupoUsuarioHeranca(GrupoUsuarioHerancaId id) {
        this.id = id;
    }

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
