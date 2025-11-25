package com.sheikplastic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sheikplastic.dto.TipoContatoDTO;
import com.sheikplastic.service.TipoContatoService;

@RestController
@RequestMapping("/api/tipos-contato")
public class TipoContatoController {

    @Autowired
    private TipoContatoService service;

    // LISTAR TODOS
    @GetMapping
    public List<TipoContatoDTO> listar() {
        return service.listar();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public TipoContatoDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // CRIAR NOVO
    @PostMapping
    public TipoContatoDTO salvar(@RequestBody TipoContatoDTO dto) {
        return service.salvar(dto);
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    public TipoContatoDTO atualizar(@PathVariable Long id, @RequestBody TipoContatoDTO dto) {
        return service.atualizar(id, dto);
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
