package com.sheikplastic.service;

import com.sheikplastic.dto.PessoaContatoDTO;
import com.sheikplastic.dto.PessoaDTO;
import com.sheikplastic.dto.PessoaMapper;
import com.sheikplastic.model.Pessoa;
import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.TipoContato;
import com.sheikplastic.repository.PessoaContatoRepository;
import com.sheikplastic.repository.PessoaRepository;
import com.sheikplastic.repository.TipoContatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaContatoRepository pessoaContatoRepository;

    @Autowired
    private TipoContatoRepository tipoContatoRepository;

    // LISTAR
    public List<PessoaDTO> listar() {
        return pessoaRepository.findAllWithContatos()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // BUSCAR POR ID
    public PessoaDTO buscar(Long id) {
        Pessoa pessoa = pessoaRepository.findByIdWithContatos(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        return toDTO(pessoa);
    }

    // CRIAR
        public PessoaDTO criar(PessoaDTO dto) {
            Pessoa pessoa = toEntity(dto);

            // Vincular pessoa nos contatos
            for (PessoaContato c : pessoa.getContatos()) {
                c.setPessoa(pessoa);
            }

            pessoa = pessoaRepository.save(pessoa);
            return toDTO(pessoa);
        }

    // ATUALIZAR
    public PessoaDTO atualizar(Long id, PessoaDTO dto) {

        Pessoa pessoa = pessoaRepository.findByIdWithContatos(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        pessoa.setTipoPessoa(dto.getTipoPessoa());
        pessoa.setDocumento(dto.getDocumento());
        pessoa.setIdentidade(dto.getIdentidade());
        pessoa.setNome(dto.getNome());
        pessoa.setApelido(dto.getApelido());
        pessoa.setDataCadastro(dto.getDataCadastro());

        pessoa.getContatos().clear();

        for (PessoaContatoDTO cDto : dto.getContatos()) {
            PessoaContato c = new PessoaContato();
            c.setSeqContato(cDto.getSeqContato());
            c.setContato(cDto.getContato());
            c.setObservacao(cDto.getObservacao());

            TipoContato tc = tipoContatoRepository.findById(cDto.getIdTipoContato())
                    .orElseThrow(() -> new RuntimeException("TipoContato inválido"));

            c.setTipoContato(tc);
            c.setPessoa(pessoa);

            pessoa.getContatos().add(c);
        }

        pessoa = pessoaRepository.save(pessoa);
        return toDTO(pessoa);
    }

    // DELETAR
    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }

    // CONVERSÃO ↓↓↓
    private PessoaDTO toDTO(Pessoa p) {
        PessoaDTO dto = new PessoaDTO();

        dto.setId(p.getId());
        dto.setTipoPessoa(p.getTipoPessoa());
        dto.setDocumento(p.getDocumento());
        dto.setIdentidade(p.getIdentidade());
        dto.setDataCadastro(p.getDataCadastro());
        dto.setNome(p.getNome());
        dto.setApelido(p.getApelido());

        dto.setContatos(
                p.getContatos().stream().map(c -> {
                    PessoaContatoDTO cd = new PessoaContatoDTO();

                      cd.setSeqContato(c.getSeqContato());
                    cd.setIdTipoContato(c.getTipoContato().getId());
                    cd.setTipoContatoDescricao(c.getTipoContato().getDescricao());
                    cd.setContato(c.getContato());
                    cd.setObservacao(c.getObservacao());

                    return cd;
                }).toList()
        );

        return dto;
    }

    private Pessoa toEntity(PessoaDTO dto) {
        Pessoa p = new Pessoa();

        p.setTipoPessoa(dto.getTipoPessoa());
        p.setDocumento(dto.getDocumento());
        p.setIdentidade(dto.getIdentidade());
        p.setDataCadastro(dto.getDataCadastro());
        p.setNome(dto.getNome());
        p.setApelido(dto.getApelido());

        if (dto.getContatos() != null) {
            dto.getContatos().forEach(cd -> {

                PessoaContato c = new PessoaContato();
                c.setSeqContato(cd.getSeqContato());
                c.setContato(cd.getContato());
                c.setObservacao(cd.getObservacao());

                TipoContato tc = tipoContatoRepository.findById(cd.getIdTipoContato())
                        .orElseThrow(() -> new RuntimeException("TipoContato inválido"));

                c.setTipoContato(tc);
                c.setPessoa(p);

                p.getContatos().add(c);
            });
        }

        return p;
    }
}
