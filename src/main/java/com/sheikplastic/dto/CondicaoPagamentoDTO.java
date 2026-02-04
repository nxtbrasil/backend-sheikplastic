package com.sheikplastic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CondicaoPagamentoDTO {

    private Integer idCondicaoPagamento;
    private String descricaoCondicaoPagamento;

    public CondicaoPagamentoDTO(Integer idCondicaoPagamento, String descricaoCondicaoPagamento) {
        this.idCondicaoPagamento = idCondicaoPagamento;
        this.descricaoCondicaoPagamento = descricaoCondicaoPagamento;
    }
}