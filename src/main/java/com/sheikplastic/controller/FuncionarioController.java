package com.sheikplastic.controller;

import com.sheikplastic.dto.FuncionarioDTO;
import com.sheikplastic.model.Funcionario;
import com.sheikplastic.model.GrupoUsuario;
import com.sheikplastic.model.GrupoUsuarioFuncionario;
import com.sheikplastic.model.GrupoUsuarioFuncionarioId;
import com.sheikplastic.repository.FuncaoRepository;
import com.sheikplastic.repository.FuncionarioRepository;
import com.sheikplastic.repository.GrupoUsuarioFuncionarioRepository;
import com.sheikplastic.repository.GrupoUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository repo;

    private final FuncaoRepository funcaoRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // ✅ Agora o bean é reconhecido

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepo;


    @Autowired
    private GrupoUsuarioFuncionarioRepository grupoUsuarioFuncionarioRepo;

    public FuncionarioController(FuncionarioRepository repo, FuncaoRepository funcaoRepo) {
        this.repo = repo;
        this.funcaoRepo = funcaoRepo;
    }

@GetMapping
public List<FuncionarioDTO> list() {
    List<Funcionario> funcionarios = repo.findAll();
    List<FuncionarioDTO> lista = new ArrayList<>();

    for (Funcionario f : funcionarios) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setIdFuncionario(f.getIdFuncionario());
        dto.setNomeFuncionario(f.getNomeFuncionario());
        dto.setEmailFuncionario(f.getEmailFuncionario());
        dto.setAtivo(f.getAtivo());
        dto.setIdFuncao(f.getIdFuncao());

        // 🔹 Buscar nome da função (se existir)
        if (f.getIdFuncao() != null) {
            funcaoRepo.findById(f.getIdFuncao()).ifPresent(funcao -> 
                dto.setNomeFuncao(funcao.getNomeFuncao())
            );
        }

        // 🔹 Buscar vínculo de grupo (pode haver 1 ou mais)
        List<GrupoUsuarioFuncionario> vinculos = grupoUsuarioFuncionarioRepo.findById_IdFuncionario(f.getIdFuncionario());
        if (!vinculos.isEmpty()) {
            // Se só pode haver 1 grupo por funcionário, pega o primeiro
            GrupoUsuario grupo = vinculos.get(0).getGrupoUsuario();
            dto.setIdGrupoUsuario(grupo.getIdGrupoUsuario());
            dto.setNomeGrupoUsuario(grupo.getNomeGrupoUsuario());
        }

        lista.add(dto);
    }

    return lista;
}

    
@GetMapping("/{id}")
public ResponseEntity<?> get(@PathVariable Long id) {
    return repo.findById(id).map(funcionario -> {
        FuncionarioDTO dto = new FuncionarioDTO();

        dto.setIdFuncionario(funcionario.getIdFuncionario());
        dto.setNomeFuncionario(funcionario.getNomeFuncionario());
        dto.setEmailFuncionario(funcionario.getEmailFuncionario());
        dto.setAtivo(funcionario.getAtivo());
        dto.setIdFuncao(funcionario.getIdFuncao());

        // 🔹 Buscar nome da função (se existir)
        if (funcionario.getIdFuncao() != null) {
            funcaoRepo.findById(funcionario.getIdFuncao())
                .ifPresent(funcao -> dto.setNomeFuncao(funcao.getNomeFuncao()));
        }

        List<GrupoUsuarioFuncionario> vinculos =
                grupoUsuarioFuncionarioRepo.findById_IdFuncionario(funcionario.getIdFuncionario());

        if (vinculos != null && !vinculos.isEmpty()) {
            GrupoUsuarioFuncionario vinculo = vinculos.get(0); // pega o primeiro
            dto.setIdGrupoUsuario(vinculo.getGrupoUsuario().getIdGrupoUsuario());
            dto.setNomeGrupoUsuario(vinculo.getGrupoUsuario().getNomeGrupoUsuario());
        }

        return ResponseEntity.ok(dto);
    }).orElseGet(() -> ResponseEntity.notFound().build());
}


    @PostMapping
public ResponseEntity<?> create(@RequestBody Funcionario f) {

  // valida email existente
    if (repo.existsByEmailFuncionario(f.getEmailFuncionario())) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Já existe um funcionário cadastrado com este e-mail: " + f.getEmailFuncionario());
    }

    if (f.getSenhaFuncionarioTexto() != null) {
        byte[] senhaBytes = f.getSenhaFuncionarioTexto().getBytes(StandardCharsets.UTF_16LE);
        f.setSenhaFuncionario(senhaBytes);
    }

    // 1️⃣ Salva o funcionário
    Funcionario saved = repo.save(f);

    // 2️⃣ Busca o grupo desejado (exemplo: grupo 3)
    GrupoUsuario grupo = grupoUsuarioRepo.findById(f.getIdGrupoUsuario())
        .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

    // 3️⃣ Cria a chave composta
    GrupoUsuarioFuncionarioId id = new GrupoUsuarioFuncionarioId();
    id.setIdFuncionario(saved.getIdFuncionario());
    id.setIdGrupoUsuario(grupo.getIdGrupoUsuario());

    // 4️⃣ Cria e salva o vínculo
    GrupoUsuarioFuncionario vinculo = new GrupoUsuarioFuncionario();
    vinculo.setId(id);
    vinculo.setFuncionario(saved);
    vinculo.setGrupoUsuario(grupo);

    grupoUsuarioFuncionarioRepo.save(vinculo);

    return ResponseEntity.ok(saved);
}

@Transactional
@PutMapping("/{id}")
public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Funcionario updated) {
    return repo.findById(id).map(f -> {

        // Atualiza os dados básicos
        f.setNomeFuncionario(updated.getNomeFuncionario());
        f.setEmailFuncionario(updated.getEmailFuncionario());
        f.setAtivo(updated.getAtivo());

        // Atualiza senha se enviada
        if (updated.getSenhaFuncionarioTexto() != null && !updated.getSenhaFuncionarioTexto().isEmpty()) {
            byte[] senhaBytes = updated.getSenhaFuncionarioTexto().getBytes(StandardCharsets.UTF_16LE);
            f.setSenhaFuncionario(senhaBytes);
        }

        // Atualiza a função, se houver
        if (updated.getIdFuncao() != null) {
            f.setIdFuncao(updated.getIdFuncao());
        }

        // Salva o funcionário atualizado
        Funcionario saved = repo.save(f);

        // 🔹 Atualiza o grupo de usuário se informado
        if (updated.getIdGrupoUsuario() != null) {

            // Apaga vínculos antigos (garante apenas um grupo por funcionário)
            grupoUsuarioFuncionarioRepo.deleteById_IdFuncionario(saved.getIdFuncionario());

            // Busca o grupo informado
            GrupoUsuario grupo = grupoUsuarioRepo.findById(updated.getIdGrupoUsuario())
                    .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

            // Cria a nova chave composta
            GrupoUsuarioFuncionarioId idVinculo = new GrupoUsuarioFuncionarioId();
            idVinculo.setIdFuncionario(saved.getIdFuncionario());
            idVinculo.setIdGrupoUsuario(grupo.getIdGrupoUsuario());

            // Cria e salva o novo vínculo
            GrupoUsuarioFuncionario vinculo = new GrupoUsuarioFuncionario();
            vinculo.setId(idVinculo);
            vinculo.setFuncionario(saved);
            vinculo.setGrupoUsuario(grupo);

            grupoUsuarioFuncionarioRepo.save(vinculo);
        }

        return ResponseEntity.ok(saved);

    }).orElseGet(() -> ResponseEntity.notFound().build());
}


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return repo.findById(id).map(f -> {
            repo.delete(f);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
