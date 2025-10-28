package com.sheikplastic.dto;

import lombok.Data;

@Data
public class FuncionarioDTO {
    private Long idFuncionario;
    private String nomeFuncionario;
    private String emailFuncionario;
    private String senhaFuncionarioTexto;
    private byte[] senhaFuncionario;
    private Boolean ativo;

    private Long idFuncao;
    private String nomeFuncao;

    private Integer idGrupoUsuario;
    private String nomeGrupoUsuario;
}
