package com.sheikplastic.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class GrupoUsuarioFuncionarioId implements Serializable {

    private Long idFuncionario;
    private Integer idGrupoUsuario;

    public GrupoUsuarioFuncionarioId(Long idFuncionario, Integer idGrupoUsuario) {
        this.idFuncionario = idFuncionario;
        this.idGrupoUsuario = idGrupoUsuario;
    }
}