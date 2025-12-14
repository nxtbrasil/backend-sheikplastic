package com.sheikplastic.service;

import org.springframework.stereotype.Service;

import com.sheikplastic.model.PessoaProduto;
import com.sheikplastic.model.PessoaProdutoId;
import com.sheikplastic.repository.PessoaProdutoRepository;

import java.util.List;

@Service
public class PessoaProdutoService {

    private final PessoaProdutoRepository repository;

    public PessoaProdutoService(PessoaProdutoRepository repository) {
        this.repository = repository;
    }

    /* ✅ LISTAR */
    public List<PessoaProduto> listarPorPessoa(Long idPessoa) {
        return repository.findByIdPessoa(idPessoa);
    }

    /* ✅ CRIAR */
    public PessoaProduto criar(PessoaProduto entity) {
        return repository.save(entity);
    }

    /* ✅ ATUALIZAR */
    public PessoaProduto atualizar(PessoaProduto entity) {
        return repository.save(entity);
    }

    /* ✅ DELETAR */
    public void deletar(Long idPessoa, Long seqProduto) {
        repository.deleteById(new PessoaProdutoId(idPessoa, seqProduto));
    }

    /* ✅ BUSCAR POR ID */
    public PessoaProduto buscar(Long idPessoa, Long seqProduto) {
        return repository.findById(new PessoaProdutoId(idPessoa, seqProduto))
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}
