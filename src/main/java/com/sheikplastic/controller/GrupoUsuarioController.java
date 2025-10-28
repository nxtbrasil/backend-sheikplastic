package com.sheikplastic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // já inclui RequestBody correto

import com.sheikplastic.model.GrupoUsuario;
import com.sheikplastic.service.GrupoUsuarioService;

@RestController
@RequestMapping("/api/grupos-usuario")
public class GrupoUsuarioController {

    @Autowired
    private GrupoUsuarioService grupoUsuarioService;

    @GetMapping
    public List<GrupoUsuario> listar() {
        return grupoUsuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoUsuario> buscarPorId(@PathVariable Long id) {
        GrupoUsuario grupo = grupoUsuarioService.buscarPorId(id);
        return ResponseEntity.ok(grupo);
    }

    @PostMapping
    public ResponseEntity<GrupoUsuario> criar(@RequestBody GrupoUsuario grupoUsuario) {
        GrupoUsuario novoGrupo = grupoUsuarioService.salvar(grupoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoGrupo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoUsuario> atualizar(@PathVariable Long id, @RequestBody GrupoUsuario grupoUsuario) {
        GrupoUsuario atualizado = grupoUsuarioService.atualizar(id, grupoUsuario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        grupoUsuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
