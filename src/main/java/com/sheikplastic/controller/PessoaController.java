package com.sheikplastic.controller;

import com.sheikplastic.dto.PessoaDTO;
import com.sheikplastic.model.Pessoa;
import com.sheikplastic.repository.PessoaRepository;
import com.sheikplastic.service.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping("/listar")
    public List<PessoaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PessoaDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public PessoaDTO criar(@RequestBody PessoaDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public PessoaDTO atualizar(@PathVariable Long id, @RequestBody PessoaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
