package com.sheikplastic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sheikplastic.model.PessoaProduto;
import com.sheikplastic.service.PessoaProdutoService;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas-produtos/{idPessoa}/produtos")
public class PessoaProdutoController {

    private final PessoaProdutoService service;

    public PessoaProdutoController(PessoaProdutoService service) {
        this.service = service;
    }

    /* ✅ LISTAR */
    @GetMapping
    public List<PessoaProduto> listar(@PathVariable Long idPessoa) {
        return service.listarPorPessoa(idPessoa);
    }

    /* ✅ BUSCAR */
    @GetMapping("/{seqProduto}")
    public PessoaProduto buscar(
            @PathVariable Long idPessoa,
            @PathVariable Long seqProduto) {
        return service.buscar(idPessoa, seqProduto);
    }

    /* ✅ CRIAR */
    @PostMapping
    public PessoaProduto criar(
            @PathVariable Long idPessoa,
            @RequestBody PessoaProduto produto) {
        produto.setIdPessoa(idPessoa);
        return service.criar(produto);
    }

    /* ✅ ATUALIZAR */
    @PutMapping("/{seqProduto}")
    public PessoaProduto atualizar(
            @PathVariable Long idPessoa,
            @PathVariable Long seqProduto,
            @RequestBody PessoaProduto produto) {

        produto.setIdPessoa(idPessoa);
        produto.setSeqProduto(seqProduto);
        return service.atualizar(produto);
    }

    /* ✅ DELETAR */
    @DeleteMapping("/{seqProduto}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long idPessoa,
            @PathVariable Long seqProduto) {

        service.deletar(idPessoa, seqProduto);
        return ResponseEntity.noContent().build();
    }
}
