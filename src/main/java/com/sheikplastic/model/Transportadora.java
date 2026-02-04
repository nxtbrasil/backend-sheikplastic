package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transportadora")
@Data
@NoArgsConstructor
public class Transportadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransportadora;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Column(length = 20)
    private String telefone;

    @Column(length = 150)
    private String email;

    // ENDEREÇO
    @Column(length = 150)
    private String logradouroEnderecoPessoa;

    @Column(length = 20)
    private String numeroEnderecoPessoa;

    @Column(length = 50)
    private String complementoEnderecoPessoa;

    @Column(length = 80)
    private String bairroEnderecoPessoa;

    // 🔹 FK Cidade
    @ManyToOne
    @JoinColumn(name = "idCidade", nullable = false)
    private Cidade cidade;

    @Column(length = 9)
    private String cepEnderecoPessoa;

    @Column(nullable = false)
    private Boolean ativo = true;

}
