package com.sheikplastic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPessoa")
    private Long id;

    @Column(name = "tipoPessoa", length = 1)
    private String tipoPessoa;

    @Column(name = "documentoPessoa")
    private String documento;

    @Column(name = "identidadePessoa")
    private String identidade;

    @Column(name = "dtCadastroPessoa")
    private LocalDate dataCadastro;

    @Column(name = "nomePessoa")
    private String nome;

    @Column(name = "apelidoPessoa")
    private String apelido;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PessoaContato> contatos = new ArrayList<>();
}
