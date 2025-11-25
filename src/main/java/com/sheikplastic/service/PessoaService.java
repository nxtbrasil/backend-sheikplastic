package com.sheikplastic.service;

import com.sheikplastic.dto.CidadeDTO;
import com.sheikplastic.dto.PessoaContatoDTO;
import com.sheikplastic.dto.PessoaDTO;
import com.sheikplastic.dto.PessoaMapper;
import com.sheikplastic.model.Cidade;
import com.sheikplastic.model.Pessoa;
import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.TipoContato;
import com.sheikplastic.repository.CidadeRepository;
import com.sheikplastic.repository.PessoaContatoRepository;
import com.sheikplastic.repository.PessoaRepository;
import com.sheikplastic.repository.TipoContatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaContatoRepository pessoaContatoRepository;

    @Autowired
    private TipoContatoRepository tipoContatoRepository;

    @Autowired
    private CidadeService cidadeService;

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
        // for (PessoaContato c : pessoa.getContatos()) {
        // c.setPessoa(pessoa);
        // }

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
        pessoa.setIdCondicaoPagamento(dto.getIdCondicaoPagamento());
        pessoa.setCepPessoaString(dto.getCepPessoaString());
        pessoa.setLogradouroPessoa(dto.getLogradouroPessoa());
        pessoa.setNumeroPessoa(dto.getNumeroPessoa());
        pessoa.setComplementoPessoa(dto.getComplementoPessoa());
        pessoa.setBairroPessoa(dto.getBairroPessoa());
        pessoa.setAtivo(dto.getAtivo());
        pessoa.setDataCadastro(LocalDate.now()); // evita NULL
        pessoa.setObservacao(dto.getObservacao());

        // -------- ATUALIZAR CIDADE --------
        if (dto.getIdCidade() != null) {
            Cidade cidade = cidadeService.buscarPorId(dto.getIdCidade())
                    .orElseThrow(() -> new RuntimeException("Cidade inválida"));
            pessoa.setCidade(cidade);
        }

        // -------- CONTATOS --------
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
    // Ao converter ENTITY -> DTO
    private PessoaDTO toDTO(Pessoa p) {
        PessoaDTO dto = new PessoaDTO();

        dto.setId(p.getId());
        dto.setTipoPessoa(p.getTipoPessoa());
        dto.setDocumento(p.getDocumento());
        dto.setIdentidade(p.getIdentidade());
        dto.setDataCadastro(p.getDataCadastro());
        dto.setNome(p.getNome());
        dto.setApelido(p.getApelido());
        dto.setIdCondicaoPagamento(p.getIdCondicaoPagamento());
        dto.setCepPessoaString(p.getCepPessoaString());
        dto.setLogradouroPessoa(p.getLogradouroPessoa());
        dto.setNumeroPessoa(p.getNumeroPessoa());
        dto.setComplementoPessoa(p.getComplementoPessoa());
        dto.setBairroPessoa(p.getBairroPessoa());
        dto.setAtivo(p.getAtivo());
        dto.setObservacao(p.getObservacao());

        // cidade
        if (p.getCidade() != null) {
            dto.setIdCidade(p.getCidade().getIdCidade());
            CidadeDTO cidadeDto = new CidadeDTO();
            cidadeDto.setIdCidade(p.getCidade().getIdCidade());
            cidadeDto.setNomeCidade(p.getCidade().getNomeCidade());
            if (p.getCidade().getEstado() != null) {
                cidadeDto.setIdEstado(p.getCidade().getEstado().getIdEstado());
                cidadeDto.setNomeEstado(p.getCidade().getEstado().getNomeEstado());
            }
            dto.setCidade(cidadeDto);
        } else {
            dto.setIdCidade(null);
            dto.setCidade(null);
        }

        if (dto.getContatos() == null) {
            dto.setContatos(
                    p.getContatos().stream().map(c -> {
                        PessoaContatoDTO cd = new PessoaContatoDTO();
                        cd.setSeqContato(c.getSeqContato());
                        if (c.getTipoContato() != null) {
                            cd.setIdTipoContato(c.getTipoContato().getId());
                            cd.setTipoContatoDescricao(c.getTipoContato().getDescricao());
                        }
                        cd.setContato(c.getContato());
                        cd.setObservacao(c.getObservacao());
                        return cd;
                    }).toList());
        }

        return dto;
    }

    // DTO -> ENTITY
    private Pessoa toEntity(PessoaDTO dto) {
        Pessoa p = new Pessoa();

        p.setTipoPessoa(dto.getTipoPessoa());
        p.setDocumento(dto.getDocumento());
        p.setIdentidade(dto.getIdentidade());
        p.setDataCadastro(dto.getDataCadastro());
        p.setNome(dto.getNome());
        p.setApelido(dto.getApelido());
        p.setIdCondicaoPagamento(dto.getIdCondicaoPagamento());
        p.setCepPessoaString(dto.getCepPessoaString());
        p.setLogradouroPessoa(dto.getLogradouroPessoa());
        p.setNumeroPessoa(dto.getNumeroPessoa());
        p.setComplementoPessoa(dto.getComplementoPessoa());
        p.setAtivo(dto.getAtivo());
        p.setDataCadastro(LocalDate.now()); // evita NULL
        p.setBairroPessoa(dto.getBairroPessoa());
        p.setObservacao(dto.getObservacao());
    
        // ------- BUSCAR CIDADE --------
        if (dto.getIdCidade() != null) {
            Cidade cidade = cidadeService.buscarPorId(dto.getIdCidade())
                    .orElseThrow(() -> new RuntimeException("Cidade inválida"));
            p.setCidade(cidade);
        } else {
            p.setCidade(null);
        }

        // ------- CONTATOS --------
        p.getContatos().clear();
        if (!dto.getContatos().isEmpty()) {
            dto.getContatos().forEach(cd -> {
                PessoaContato c = new PessoaContato();
                c.setSeqContato(cd.getSeqContato());
                c.setContato(cd.getContato());
                c.setObservacao(cd.getObservacao());

                if (cd.getIdTipoContato() != null) {
                    TipoContato tc = tipoContatoRepository.findById(cd.getIdTipoContato())
                            .orElseThrow(() -> new RuntimeException("TipoContato inválido"));
                    c.setTipoContato(tc);
                }

                c.setPessoa(p);
                p.getContatos().add(c);
            });
        }

        return p;
    }

}
