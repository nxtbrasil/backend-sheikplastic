package com.sheikplastic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheikplastic.dto.TipoContatoDTO;
import com.sheikplastic.dto.TipoContatoMapper;
import com.sheikplastic.model.TipoContato;
import com.sheikplastic.repository.TipoContatoRepository;

@Service
public class TipoContatoService {

    @Autowired
    private TipoContatoRepository repository;

    @Autowired
    private TipoContatoMapper mapper;


    public List<TipoContatoDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public TipoContatoDTO buscarPorId(Long id) {
        TipoContato tipo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoContato não encontrado"));
        return mapper.toDTO(tipo);
    }

    public TipoContatoDTO salvar(TipoContatoDTO dto) {
        TipoContato salvo = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(salvo);
    }

    public TipoContatoDTO atualizar(Long id, TipoContatoDTO dto) {
        TipoContato existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoContato não encontrado"));

        existente.setDescricao(dto.getDescricao());

        return mapper.toDTO(repository.save(existente));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
