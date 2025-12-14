package com.sheikplastic.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class PedidoItemId implements Serializable {

    @Column(name = "idPedido")
    private Long idPedido;

    @Column(name = "idPessoa")
    private Long idPessoa;

    @Column(name = "seqProduto")
    private Long seqProduto;

    // equals + hashCode (OBRIGATÓRIO)
}
