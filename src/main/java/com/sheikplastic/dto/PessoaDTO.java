package com.sheikplastic.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sheikplastic.model.Cidade;

import lombok.Data;

@Data
public class PessoaDTO {

    private Long id;
    private String tipoPessoa;
    private String documento;
    private String identidade;
    private LocalDate dataCadastro;
    private String nome;
    private String apelido;
    private Long idCondicaoPagamento;
    private Long idCidade;   // <-- AQUI EXISTE, ENTÃO O GETTER DEVERIA EXISTIR
    private String cepPessoaString;
    private String logradouroPessoa;
    private String numeroPessoa;
    private String complementoPessoa;
    private String bairroPessoa;
    private Boolean ativo;
    private String observacao;

    private List<PessoaContatoDTO> contatos = new ArrayList<>();
    private CidadeDTO cidade; // opcional, popula se quiser enviar os dados completos da cidade


}