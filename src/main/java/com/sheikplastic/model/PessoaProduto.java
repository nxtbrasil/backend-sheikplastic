package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "PessoaProduto")
@IdClass(PessoaProdutoId.class)
@Data
public class PessoaProduto {

    /* =========================
       CHAVE COMPOSTA
    ========================== */
    @Id
    @Column(name = "idPessoa")
    private Long idPessoa;

    @Id
    @Column(name = "seqProduto")
    private Long seqProduto;

    /* =========================
       RELACIONAMENTO PRODUTO
    ========================== */
    @ManyToOne
    @JoinColumn(
        name = "idProduto",
        referencedColumnName = "idProduto",
        insertable = false,
        updatable = false
    )
    private Produto produto;

    /* =========================
       CAMPOS
    ========================== */
    @Column(name = "idProduto")
    private Long idProduto;

    @Column(name = "complementoProduto")
    private String complementoProduto;

    @Column(name = "unpProduto")
    private String unpProduto;

    @Column(name = "unvProduto")
    private String unvProduto;

    @Column(name = "valorVenda")
    private BigDecimal valorVenda;

    @Transient
    private BigDecimal valorVendaAnterior;
}
