package com.sheikplastic.service;

import com.sheikplastic.model.Transportadora;
import com.sheikplastic.repository.TransportadoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportadoraService {

    private final TransportadoraRepository repository;

    public List<Transportadora> listarTodas() {
        return repository.findAll();
    }

    public List<Transportadora> listarAtivas() {
        return repository.findByAtivoTrue();
    }

    public Transportadora buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transportadora não encontrada"));
    }

    public Transportadora salvar(Transportadora transportadora) {
        return repository.save(transportadora);
    }

    public Transportadora atualizar(Integer id, Transportadora dados) {
        Transportadora t = buscarPorId(id);

        t.setNome(dados.getNome());
        t.setCnpj(dados.getCnpj());
        t.setTelefone(dados.getTelefone());
        t.setEmail(dados.getEmail());
        t.setLogradouroEnderecoPessoa(dados.getLogradouroEnderecoPessoa());
        t.setNumeroEnderecoPessoa(dados.getNumeroEnderecoPessoa());
        t.setComplementoEnderecoPessoa(dados.getComplementoEnderecoPessoa());
        t.setBairroEnderecoPessoa(dados.getBairroEnderecoPessoa());
        t.setCidade(dados.getCidade());
        t.setCepEnderecoPessoa(dados.getCepEnderecoPessoa());
        t.setAtivo(dados.getAtivo());

        return repository.save(t);
    }

    public void desativar(Integer id) {
        Transportadora t = buscarPorId(id);
        t.setAtivo(false);
        repository.save(t);
    }
}
