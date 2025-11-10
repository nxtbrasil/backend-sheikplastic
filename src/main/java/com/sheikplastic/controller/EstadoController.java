package com.sheikplastic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sheikplastic.model.Estado;
import com.sheikplastic.repository.EstadoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    // CREATE
    @PostMapping
    public ResponseEntity<Estado> criar(@RequestBody Estado estado) {
        Estado novoEstado = estadoRepository.save(estado);
        return ResponseEntity.ok(novoEstado);
    }

    // READ - listar todos
    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    // READ - buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscarPorId(@PathVariable Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);
        return estado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estadoAtualizado) {
        return estadoRepository.findById(id).map(estado -> {
            estado.setSiglaEstado(estadoAtualizado.getSiglaEstado());
            estado.setNomeEstado(estadoAtualizado.getNomeEstado());
            Estado atualizado = estadoRepository.save(estado);
            return ResponseEntity.ok(atualizado);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (estadoRepository.existsById(id)) {
            estadoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
