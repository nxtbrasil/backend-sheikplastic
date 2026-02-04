package com.sheikplastic.controller;

import com.sheikplastic.model.Transportadora;
import com.sheikplastic.service.TransportadoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportadoras")
@RequiredArgsConstructor
public class TransportadoraController {

    private final TransportadoraService service;

    @GetMapping
    public List<Transportadora> listar() {
        return service.listarTodas();
    }

    @GetMapping("/ativas")
    public List<Transportadora> listarAtivas() {
        return service.listarAtivas();
    }

    @GetMapping("/{id}")
    public Transportadora buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Transportadora criar(@RequestBody Transportadora transportadora) {
        return service.salvar(transportadora);
    }

    @PutMapping("/{id}")
    public Transportadora atualizar(
            @PathVariable Integer id,
            @RequestBody Transportadora transportadora) {
        return service.atualizar(id, transportadora);
    }

    @DeleteMapping("/{id}")
    public void desativar(@PathVariable Integer id) {
        service.desativar(id);
    }
}
