package com.sheikplastic.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GrupoUsuarioHerancaId implements Serializable {

    @Column(name = "idGrupoPai")
    private Integer idGrupoPai;

    @Column(name = "idGrupoFilho")
    private Integer idGrupoFilho;

    // Getters e setters
    public Integer getIdGrupoPai() {
        return idGrupoPai;
    }

    public void setIdGrupoPai(Integer idGrupoPai) {
        this.idGrupoPai = idGrupoPai;
    }

    public Integer getIdGrupoFilho() {
        return idGrupoFilho;
    }

    public void setIdGrupoFilho(Integer idGrupoFilho) {
        this.idGrupoFilho = idGrupoFilho;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrupoUsuarioHerancaId)) return false;
        GrupoUsuarioHerancaId that = (GrupoUsuarioHerancaId) o;
        return Objects.equals(idGrupoPai, that.idGrupoPai) &&
               Objects.equals(idGrupoFilho, that.idGrupoFilho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGrupoPai, idGrupoFilho);
    }
}
