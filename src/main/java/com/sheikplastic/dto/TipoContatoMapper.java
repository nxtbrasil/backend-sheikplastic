package com.sheikplastic.dto;

import org.springframework.stereotype.Component;

import com.sheikplastic.model.TipoContato;

@Component
public class TipoContatoMapper {

    public TipoContatoDTO toDTO(TipoContato entity) {
        TipoContatoDTO dto = new TipoContatoDTO();
        dto.setId(entity.getId());
        dto.setDescricao(entity.getDescricao());
        return dto;
    }

    public TipoContato toEntity(TipoContatoDTO dto) {
        TipoContato entity = new TipoContato();
        entity.setId(dto.getId());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }
}
