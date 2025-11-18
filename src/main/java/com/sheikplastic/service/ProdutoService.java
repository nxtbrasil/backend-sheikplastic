package com.sheikplastic.service;

import com.sheikplastic.model.Produto;
import com.sheikplastic.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

     public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public List<Produto> listarPorNome(String nomeProduto) {
        if (nomeProduto != null && !nomeProduto.isEmpty()) {
            return repository.findByNomeProdutoContainingIgnoreCase(nomeProduto);
        }
        return repository.findAll();
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}
