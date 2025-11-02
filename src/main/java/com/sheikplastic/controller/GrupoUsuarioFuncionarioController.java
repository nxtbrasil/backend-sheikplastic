package com.sheikplastic.controller;

import com.sheikplastic.dto.FuncionarioGrupoDTO;
import com.sheikplastic.model.GrupoUsuarioFuncionario;
import com.sheikplastic.service.GrupoUsuarioFuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/grupo-usuario-funcionario")
public class GrupoUsuarioFuncionarioController {

    @Autowired
    private GrupoUsuarioFuncionarioService service;

    @PostMapping("/vincular")
    public void vincular(@RequestParam Long idFuncionario, @RequestParam Integer idGrupoUsuario) {
        service.vincularFuncionarioAoGrupo(idFuncionario, idGrupoUsuario);
    }

    @PutMapping("/atualizar")
    public void atualizarVinculo(@RequestParam Long idFuncionario, @RequestParam Integer idGrupoUsuario) {
        service.atualizarVinculoFuncionarioAoGrupo(idFuncionario, idGrupoUsuario);
    }

    @DeleteMapping("/desvincular/{idFuncionario}")
    public void desvincular(@PathVariable Long idFuncionario) {
        service.desvincularFuncionario(idFuncionario);
    }

    @GetMapping("/{idFuncionario}")
    public List<GrupoUsuarioFuncionario> listar(@PathVariable Long idFuncionario) {
        return service.listarPorFuncionario(idFuncionario);
    }

    // ✅ Novo endpoint
    @GetMapping
    public List<FuncionarioGrupoDTO> listarTodosComVinculo() {
        return service.listarTodosComVinculo();
    }
}
