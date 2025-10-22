package com.sheikplastic.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class GrupoUsuarioHerancaId implements Serializable {
    private Integer idGrupoPai;
    private Integer idGrupoFilho;
}
