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

    public Optional<Cidade> buscarPorId(Long id) {
        return cidadeRepository.findById(id);
    }

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Cidade atualizar(Long id, Cidade cidadeAtualizada) {
        return cidadeRepository.findById(id)
                .map(cidade -> {
                    cidade.setNomeCidade(cidadeAtualizada.getNomeCidade());
                    cidade.setEstado(cidadeAtualizada.getEstado());
                    return cidadeRepository.save(cidade);
                })
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));
    }

    public void deletar(Long id) {
        cidadeRepository.deleteById(id);
    }

    public List<Cidade> listarPorEstado(Long idEstado) {
    return cidadeRepository.findByEstado_IdEstado(idEstado);
}

}
