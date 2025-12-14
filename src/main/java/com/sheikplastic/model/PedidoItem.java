package com.sheikplastic.model;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PedidoItem")
public class PedidoItem {

    @EmbeddedId
    private PedidoItemId id;

    private BigDecimal valorVenda;
    private BigDecimal qtdItem;

    // getters e setters
}