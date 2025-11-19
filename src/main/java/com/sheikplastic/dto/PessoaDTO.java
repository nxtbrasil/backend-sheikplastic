package com.sheikplastic.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private List<PessoaContatoDTO> contatos = new ArrayList<>();

}