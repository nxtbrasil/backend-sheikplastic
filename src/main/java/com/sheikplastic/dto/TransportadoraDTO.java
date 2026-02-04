package com.sheikplastic.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TransportadoraDTO {

    private Long idTransportadora;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String logradouroEnderecoPessoa;
    private String numeroEnderecoPessoa;
    private String complementoEnderecoPessoa;
    private String bairroEnderecoPessoa;
    private String cepEnderecoPessoa;
    private Boolean ativo;
    private LocalDate dataCadastro;
    
    // Opcional: Se quiser retornar os dados da cidade da transportadora
    private CidadeDTO cidade; 
}