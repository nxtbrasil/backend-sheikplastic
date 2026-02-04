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

    @Column(name = "nomePessoa", length = 50)
    private String nome;

    @Column(name = "apelidoPessoa", length = 50)
    private String apelido;

    @Column(name = "cepEnderecoPessoa", length = 8)
    private String cepPessoaString;

    @Column(name = "logradouroEnderecoPessoa", length = 100)
    private String logradouroPessoa;

    @Column(name = "numeroEnderecoPessoa", length = 10)
    private String numeroPessoa;

    @Column(name = "complementoEnderecoPessoa", length = 20)
    private String complementoPessoa;

    // 🔥 FK Cidade
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCidade")
    private Cidade cidade;

    // 🔥 FK Transportadora (ADICIONADO)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTransportadora")
    private Transportadora transportadora;

    @Column(name = "bairroEnderecoPessoa", length = 50)
    private String bairroPessoa;

    @Column(name = "idCondicaoPagamento")
    private Long idCondicaoPagamento;

    @Column(name = "observacao", length = 50)
    private String observacao;

    @Column(name = "ativo", length = 1)
    private Boolean ativo;

    // Relacionamento com contatos
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PessoaContato> contatos = new ArrayList<>();
}
