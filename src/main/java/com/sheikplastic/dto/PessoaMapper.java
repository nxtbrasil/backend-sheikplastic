package com.sheikplastic.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sheikplastic.model.Pessoa;
import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.TipoContato;

@Component
public class PessoaMapper {

    // ==== ENTITY → DTO =====
    public PessoaDTO toDTO(Pessoa pessoa) {

        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setIdentidade(pessoa.getIdentidade());

        if (pessoa.getContatos() != null) {
            dto.setContatos(
                pessoa.getContatos().stream()
                        .map(this::toContatoDTO)
                        .toList()
            );
        }

        return dto;
    }

    public PessoaContatoDTO toContatoDTO(PessoaContato contato) {

        PessoaContatoDTO dto = new PessoaContatoDTO();
        dto.setSeqContato(contato.getSeqContato());
        dto.setContato(contato.getContato());
        dto.setObservacao(contato.getObservacao());

        if (contato.getTipoContato() != null) {
            dto.setIdTipoContato(contato.getTipoContato().getId());
        }

        return dto;
    }


    // ===== DTO → ENTITY =====

    public Pessoa toEntity(PessoaDTO dto) {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(dto.getId());
        pessoa.setNome(dto.getNome());
        pessoa.setIdentidade(dto.getIdentidade());

        if (dto.getContatos() != null) {
            pessoa.setContatos(
                dto.getContatos().stream()
                        .map(this::toContatoEntity)
                        .toList()
            );
        }

        return pessoa;
    }

    public PessoaContato toContatoEntity(PessoaContatoDTO dto) {

        PessoaContato contato = new PessoaContato();
        contato.setSeqContato(dto.getSeqContato());
        contato.setContato(dto.getContato());
        contato.setObservacao(dto.getObservacao());

        if (dto.getIdTipoContato() != null) {
            TipoContato tipo = new TipoContato();
            tipo.setId(dto.getIdTipoContato());
            contato.setTipoContato(tipo);
        }

        return contato;
    }
}
