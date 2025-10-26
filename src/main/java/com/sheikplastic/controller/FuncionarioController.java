package com.sheikplastic.controller;

import com.sheikplastic.model.Funcionario;
import com.sheikplastic.repository.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository repo;

      @Autowired
    private BCryptPasswordEncoder passwordEncoder; // ✅ Agora o bean é reconhecido

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
public ResponseEntity<?> create(@RequestBody Funcionario f) {
    if (f.getSenhaFuncionarioTexto() != null) {
        byte[] senhaBytes = f.getSenhaFuncionarioTexto().getBytes(StandardCharsets.UTF_16LE);
        f.setSenhaFuncionario(senhaBytes);
    }

    Funcionario saved = repo.save(f);
    return ResponseEntity.ok(saved);
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
