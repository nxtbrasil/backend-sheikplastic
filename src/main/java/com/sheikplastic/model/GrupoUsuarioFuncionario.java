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

    @ManyToOne
    @MapsId("idFuncionario")
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;

    @ManyToOne
    @MapsId("idGrupoUsuario")
    @JoinColumn(name = "idGrupoUsuario")
    private GrupoUsuario grupoUsuario;
}
