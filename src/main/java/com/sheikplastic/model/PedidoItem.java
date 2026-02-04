package com.sheikplastic.model;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PedidoItem")
@IdClass(PedidoItemId.class)
@Data
public class PedidoItem {

    @Id
    private Long idPedido;

    @Id
    private Long idPessoa;

    @Id
    private Long seqProduto;

    private BigDecimal valorVenda;
    private BigDecimal qtdItem;
    private String unpItem;

    private BigDecimal qtdEntregue;
    private String unvItem;
}
