package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFuncionario")
    private Long idFuncionario;

    @Column(name = "nomeFuncionario", nullable = false, length = 50)
    private String nomeFuncionario;

    @Column(name = "idFuncao")
    private Long idFuncao;

    @Column(name = "emailFuncionario", nullable = false, length = 100)
    private String emailFuncionario;

    @Column(name = "senhaFuncionario")
    private byte[] senhaFuncionario;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;
}