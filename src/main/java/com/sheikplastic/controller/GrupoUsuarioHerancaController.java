package com.sheikplastic.controller;

import com.sheikplastic.dto.GrupoUsuarioHerancaResponse;
import com.sheikplastic.service.GrupoUsuarioHerancaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@CrossOrigin
public class GrupoUsuarioHerancaController {

    private final GrupoUsuarioHerancaService service;

    public GrupoUsuarioHerancaController(GrupoUsuarioHerancaService service) {
        this.service = service;
    }

    // 🔹 LISTAR HERANÇAS (GRUPOS FILHOS) DE UM GRUPO PAI
    @GetMapping("/{idGrupoPai}/herancas")
    public ResponseEntity<GrupoUsuarioHerancaResponse> listarHerancas(
            @PathVariable Integer idGrupoPai,
            @RequestParam(required = false) String filtro) {

        GrupoUsuarioHerancaResponse response = service.listarGruposFilhos(idGrupoPai, filtro);
        return ResponseEntity.ok(response);
    }

    // 🔹 CRIAR VÍNCULO (VINCULAR GRUPO PAI E FILHO)
    @PostMapping("/{idGrupoPai}/herancas/{idGrupoFilho}")
    public ResponseEntity<String> vincularGrupo(
            @PathVariable Integer idGrupoPai,
            @PathVariable Integer idGrupoFilho) {

        service.vincular(idGrupoPai, idGrupoFilho);
        return ResponseEntity.ok("Vínculo criado com sucesso entre os grupos.");
    }

    // 🔹 REMOVER VÍNCULO (DESVINCULAR GRUPO PAI E FILHO)
    @DeleteMapping("/{idGrupoPai}/herancas/{idGrupoFilho}")
    public ResponseEntity<String> desvincularGrupo(
            @PathVariable Integer idGrupoPai,
            @PathVariable Integer idGrupoFilho) {

        service.desvincular(idGrupoPai, idGrupoFilho);
        return ResponseEntity.ok("Vínculo removido com sucesso.");
    }


    // 🔹 VERIFICAR SE DOIS GRUPOS ESTÃO VINCULADOS
    @GetMapping("/{idGrupoPai}/herancas/{idGrupoFilho}/vinculado")
    public ResponseEntity<Boolean> isVinculado(
            @PathVariable Integer idGrupoPai,
            @PathVariable Integer idGrupoFilho) {

        boolean vinculado = service.isVinculado(idGrupoPai, idGrupoFilho);
        return ResponseEntity.ok(vinculado);
    }
}
