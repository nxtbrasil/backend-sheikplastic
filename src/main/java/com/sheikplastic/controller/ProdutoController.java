package com.sheikplastic.controller;

import com.sheikplastic.model.Produto;
import com.sheikplastic.service.PessoaProdutoService;
import com.sheikplastic.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService service;

    private final PessoaProdutoService pessoaProdutoService;

    public ProdutoController(ProdutoService service, PessoaProdutoService pessoaProdutoService) {
        this.service = service;
        this.pessoaProdutoService = pessoaProdutoService;
    }

    @GetMapping
    public List<Produto> listar(@RequestParam(required = false) String nomeProduto) {
        if (nomeProduto != null && !nomeProduto.isEmpty()) {
            return service.listarPorNome(nomeProduto);
        }
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto) {
        return service.salvar(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        produto.setIdProduto(id);
        return service.salvar(produto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

        @GetMapping("/combo")
    public List<Produto> listarParaCombo() {
        return pessoaProdutoService.listarParaCombo();
    }

}
