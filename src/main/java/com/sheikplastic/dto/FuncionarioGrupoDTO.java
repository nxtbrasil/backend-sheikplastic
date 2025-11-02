package com.sheikplastic.dto;

import lombok.Data;

@Data
public class FuncionarioGrupoDTO {
    private Long idFuncionario;
    private String nomeFuncionario;
    private String emailFuncionario;
    private boolean vinculado;
    private String nomeGrupo;
}
