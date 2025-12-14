package com.sheikplastic.model;

import java.io.Serializable;
import java.util.Objects;

public class PessoaProdutoId implements Serializable {

    private Long idPessoa;
    private Long seqProduto;

    public PessoaProdutoId() {}

    public PessoaProdutoId(Long idPessoa, Long seqProduto) {
        this.idPessoa = idPessoa;
        this.seqProduto = seqProduto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PessoaProdutoId)) return false;
        PessoaProdutoId that = (PessoaProdutoId) o;
        return Objects.equals(idPessoa, that.idPessoa) &&
               Objects.equals(seqProduto, that.seqProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPessoa, seqProduto);
    }
}
