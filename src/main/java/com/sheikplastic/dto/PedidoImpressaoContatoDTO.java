package com.sheikplastic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PedidoImpressaoContatoDTO {

    private Long idPessoa;
    private Integer seqContato;
    private Long id;
    private String descricao;
    private String contato;
    private String observacao;

    public PedidoImpressaoContatoDTO(
        Long idPessoa,
        Integer seqContato,
        Long id,
        String descricao,
        String contato,
        String observacao
    ) {
        this.idPessoa = idPessoa;
        this.seqContato = seqContato;
        this.id = id;
        this.descricao = descricao;
        this.contato = contato;
        this.observacao = observacao;
    }
}
