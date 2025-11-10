package com.sheikplastic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sheikplastic.model.CondicaoPagamento;
import com.sheikplastic.repository.CondicaoPagamentoRepository;

@RestController
@RequestMapping("/api/condicoes-pagamento")
public class CondicaoPagamentoController {

    @Autowired
    private CondicaoPagamentoRepository repository;

    @GetMapping
    public List<CondicaoPagamento> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondicaoPagamento> buscarPorId(@PathVariable Integer id) {
        Optional<CondicaoPagamento> condicao = repository.findById(id);
        return condicao.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CondicaoPagamento criar(@RequestBody CondicaoPagamento condicao) {
        return repository.save(condicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondicaoPagamento> atualizar(@PathVariable Integer id,
                                                       @RequestBody CondicaoPagamento dadosAtualizados) {
        return repository.findById(id)
                .map(condicao -> {
                    condicao.setDescricaoCondicaoPagamento(dadosAtualizados.getDescricaoCondicaoPagamento());
                    repository.save(condicao);
                    return ResponseEntity.ok(condicao);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        return repository.findById(id)
                .map(condicao -> {
                    try {
                        repository.delete(condicao);
                        return ResponseEntity.ok().build();
                    } catch (org.springframework.dao.DataIntegrityViolationException e) {
                        return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body("Não é possível excluir: existem registros vinculados a esta condição de pagamento.");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
