package com.sheikplastic.service;

import com.sheikplastic.dto.HistoricoPrecoDTO;
import com.sheikplastic.repository.HistoricoPrecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoPrecoService {

    private final HistoricoPrecoRepository repository;

    public HistoricoPrecoService(HistoricoPrecoRepository repository) {
        this.repository = repository;
    }

    public List<HistoricoPrecoDTO> buscarHistorico(Long idPessoa, Long seqProduto) {
        return repository.buscarHistorico(idPessoa, seqProduto);
    }
}
