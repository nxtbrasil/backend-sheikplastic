package com.sheikplastic.service;

import com.sheikplastic.model.Cidade;
import com.sheikplastic.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }

    public Optional<Cidade> buscarPorId(Integer id) {
        return cidadeRepository.findById(id);
    }

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Cidade atualizar(Integer id, Cidade cidadeAtualizada) {
        return cidadeRepository.findById(id).map(cidade -> {
            cidade.setNomeCidade(cidadeAtualizada.getNomeCidade());
            cidade.setIdEstado(cidadeAtualizada.getIdEstado());
            return cidadeRepository.save(cidade);
        }).orElseThrow(() -> new RuntimeException("Cidade não encontrada"));
    }

    public void deletar(Integer id) {
        cidadeRepository.deleteById(id);
    }
}
