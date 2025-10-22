package com.sheikplastic.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class GrupoUsuarioFuncionarioId implements Serializable {
    private Long idFuncionario;
    private Integer idGrupoUsuario;
}
