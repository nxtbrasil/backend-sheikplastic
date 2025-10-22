package com.sheikplastic.dto;

import lombok.Data;

@Data
public class FuncionarioDTO {
    private Long idFuncionario;
    private String nomeFuncionario;
    private String emailFuncionario;
    private Boolean ativo;
}
