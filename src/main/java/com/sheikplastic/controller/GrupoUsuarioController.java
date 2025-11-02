package com.sheikplastic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // já inclui RequestBody correto

import com.sheikplastic.model.GrupoUsuario;
import com.sheikplastic.model.Regra;
import com.sheikplastic.service.GrupoUsuarioRegraService;
import com.sheikplastic.service.GrupoUsuarioService;
import com.sheikplastic.service.RegraService;

@RestController
@RequestMapping("/api/grupos-usuario")
public class GrupoUsuarioController {

    @Autowired
    private GrupoUsuarioService grupoUsuarioService;

    @Autowired
    private GrupoUsuarioRegraService grupoUsuarioRegraService;

    @Autowired
    private RegraService regraService;

    @GetMapping
    public List<GrupoUsuario> listar() {
        return grupoUsuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoUsuario> buscarPorId(@PathVariable Integer id) {
        GrupoUsuario grupo = grupoUsuarioService.buscarPorId(id);
        return ResponseEntity.ok(grupo);
    }

    @PostMapping
    public ResponseEntity<GrupoUsuario> criar(@RequestBody GrupoUsuario grupoUsuario) {
        GrupoUsuario novoGrupo = grupoUsuarioService.salvar(grupoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoGrupo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoUsuario> atualizar(@PathVariable Integer id, @RequestBody GrupoUsuario grupoUsuario) {
        GrupoUsuario atualizado = grupoUsuarioService.atualizar(id, grupoUsuario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        grupoUsuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retorna todas as regras diretas e herdadas de um grupo.
     * Exemplo: GET /api/grupos-usuario/1/regras
     */
    @GetMapping("/{id}/regras")
    public List<Regra> listarRegras(@PathVariable Integer id) {
        return grupoUsuarioRegraService.listarRegrasComHeranca(id);
    }

    // ✅ Listar todas as regras
    @GetMapping("/regras")
    public ResponseEntity<List<Regra>> listarRegras() {
        List<Regra> regras = regraService.listarTodas();
        return ResponseEntity.ok(regras);
    }

    @GetMapping("/regras/{id}")
    public ResponseEntity<Regra> buscarPorIdRegra(@PathVariable Long id) {
        Regra regra = regraService.buscarPorId(id);
        return ResponseEntity.ok(regra);
    }

    @PostMapping("/regras")
    public ResponseEntity<Regra> criar(@RequestBody Regra regra) {
        Regra nova = regraService.salvar(regra);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @PutMapping("/regras/{id}")
    public ResponseEntity<Regra> atualizar(@PathVariable Long id, @RequestBody Regra regra) {
        Regra atualizada = regraService.atualizar(id, regra);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/regras/{id}")
    public ResponseEntity<Void> deletarResgra(@PathVariable Long id) {
        regraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
