package com.sheikplastic.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class GrupoUsuarioRegraId implements Serializable {
    private Integer idGrupoUsuario;
    private Long idRegra;
}
