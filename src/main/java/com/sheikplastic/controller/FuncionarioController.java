package com.sheikplastic.controller;

import com.sheikplastic.model.Funcionario;
import com.sheikplastic.repository.FuncionarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository repo;

    public FuncionarioController(FuncionarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Funcionario> list(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        return repo.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Funcionario f){
        return ResponseEntity.ok(repo.save(f));
    }

@PutMapping("/{id}")
public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Funcionario updated) {
    return repo.findById(id).map(f -> {
        f.setNomeFuncionario(updated.getNomeFuncionario());
        f.setEmailFuncionario(updated.getEmailFuncionario());
        f.setAtivo(updated.getAtivo());

        // Atualiza a senha apenas se for enviada e não estiver vazia
        if (updated.getSenhaFuncionario() != null && updated.getSenhaFuncionario().length > 0) {
            f.setSenhaFuncionario(updated.getSenhaFuncionario());
        }

        repo.save(f);
        return ResponseEntity.ok().build();
    }).orElseGet(() -> ResponseEntity.notFound().build());
}


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return repo.findById(id).map(f -> {
            repo.delete(f);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
