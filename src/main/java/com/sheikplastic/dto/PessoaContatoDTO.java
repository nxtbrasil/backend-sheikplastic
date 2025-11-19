package com.sheikplastic.dto;

import lombok.Data;

@Data
public class PessoaContatoDTO {

    private Long id;
    private Integer seqContato;
    private Long idTipoContato;
    private String tipoContatoDescricao;
    private String contato;
    private String observacao;
}
