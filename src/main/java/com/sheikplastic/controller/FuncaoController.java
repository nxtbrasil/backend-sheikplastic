package com.sheikplastic.controller;

import com.sheikplastic.model.Funcao;
import com.sheikplastic.service.FuncaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcoes")
public class FuncaoController {

    private final FuncaoService funcaoService;

    public FuncaoController(FuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }

    @GetMapping
    public ResponseEntity<List<Funcao>> listarTodas() {
        return ResponseEntity.ok(funcaoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcao> buscarPorId(@PathVariable Long id) {
        return funcaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Funcao> criar(@RequestBody Funcao funcao) {
        return ResponseEntity.ok(funcaoService.salvar(funcao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcao> atualizar(@PathVariable Long id, @RequestBody Funcao funcao) {
        try {
            return ResponseEntity.ok(funcaoService.atualizar(id, funcao));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}