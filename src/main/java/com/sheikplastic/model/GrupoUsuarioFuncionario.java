package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GrupoUsuarioFuncionario")
@Data
@NoArgsConstructor
public class GrupoUsuarioFuncionario {

    @EmbeddedId
    private GrupoUsuarioFuncionarioId id;

    @MapsId("idFuncionario")
    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;

    @MapsId("idGrupoUsuario")
    @ManyToOne
    @JoinColumn(name = "idGrupoUsuario")
    private GrupoUsuario grupoUsuario;
}
