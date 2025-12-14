package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "PessoaProduto")
@IdClass(PessoaProdutoId.class)
@Data
public class PessoaProduto {

    @Id
    @Column(name = "idPessoa")
    private Long idPessoa;

    @Id
    @Column(name = "seqProduto")
    private Long seqProduto;

    @Column(name = "idProduto", nullable = false)
    private Long idProduto;

    @Column(name = "complementoProduto")
    private String complementoProduto;

    @Column(name = "unpProduto")
    private String unpProduto;

    @Column(name = "unvProduto")
    private String unvProduto;

    @Column(name = "valorVenda")
    private BigDecimal valorVenda;

    /* GETTERS E SETTERS */
}
