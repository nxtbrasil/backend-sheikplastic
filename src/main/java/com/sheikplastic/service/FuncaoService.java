package com.sheikplastic.service;

import com.sheikplastic.model.Funcao;
import com.sheikplastic.repository.FuncaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncaoService {

    private final FuncaoRepository funcaoRepository;

    public FuncaoService(FuncaoRepository funcaoRepository) {
        this.funcaoRepository = funcaoRepository;
    }

    public List<Funcao> listarTodas() {
        return funcaoRepository.findAll();
    }

    public Optional<Funcao> buscarPorId(Long id) {
        return funcaoRepository.findById(id);
    }

    public Funcao salvar(Funcao funcao) {
        return funcaoRepository.save(funcao);
    }

    public Funcao atualizar(Long id, Funcao funcaoAtualizada) {
        return funcaoRepository.findById(id)
                .map(funcao -> {
                    funcao.setNomeFuncao(funcaoAtualizada.getNomeFuncao());
                    return funcaoRepository.save(funcao);
                })
                .orElseThrow(() -> new RuntimeException("Função não encontrada"));
    }

    public void deletar(Long id) {
        funcaoRepository.deleteById(id);
    }
}
