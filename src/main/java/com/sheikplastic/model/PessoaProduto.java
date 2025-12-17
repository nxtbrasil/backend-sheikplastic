package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

// ...
@Entity
@Table(name = "PessoaProduto")
@IdClass(PessoaProdutoId.class)
@Data
public class PessoaProduto {

    // ... (Relacionamento Produto)

    // 2. Chave Primária Composta (usando @IdClass)

    @ManyToOne
    @JoinColumn(name = "seqProduto", referencedColumnName = "idProduto", insertable = false, updatable = false)
    private Produto produto;

    @Id
    @Column(name = "idPessoa")
    private Long idPessoa;

    @Id
    @Column(name = "seqProduto")
    private Long seqProduto;

    // REMOVER ESTE CAMPO:
    // @Id
    // @Column(name = "seqRelacao")
    // private Long seqRelacao;

    // 3. Colunas do PessoaProduto (verifique se os campos de retorno estão aqui!)
    @Column(name = "complementoProduto")
    private String complementoProduto;

    @Column(name = "unpProduto") // Campo que causou erro de compilação
    private String unpProduto;

    @Column(name = "unvProduto") // Campo que causou erro de compilação
    private String unvProduto;

    @Column(name = "valorVenda") // Campo que causou erro de compilação
    private BigDecimal valorVenda;

}