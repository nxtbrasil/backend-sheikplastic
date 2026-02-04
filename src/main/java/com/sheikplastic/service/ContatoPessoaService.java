package com.sheikplastic.service;

import com.sheikplastic.dto.PessoaContatoDTO;
import com.sheikplastic.model.Pessoa;
import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.PessoaContatoId;
import com.sheikplastic.model.TipoContato;
import com.sheikplastic.repository.PessoaContatoRepository;
import com.sheikplastic.repository.PessoaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoPessoaService {

    @Autowired
    private PessoaContatoRepository pessoaContatoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    /**
     * Lista contatos por pessoa
     */
    public List<PessoaContato> listarPorPessoa(Long idPessoa) {
        return pessoaContatoRepository.findByPessoa_IdOrderById_SeqContato(idPessoa);
    }

    /**
     * Buscar contato pelo ID composto
     */
    public Optional<PessoaContato> buscarPorId(PessoaContatoId id) {
        return pessoaContatoRepository.findById(id);
    }

    /**
     * Criar novo contato
     */
    public PessoaContato salvar(PessoaContato contato) {

        Pessoa pessoa = pessoaRepository
                .findById(contato.getId().getIdPessoa())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        contato.setPessoa(pessoa);

        return pessoaContatoRepository.save(contato);
    }

    public PessoaContato atualizarContato(PessoaContato contatoAtualizado) {

        PessoaContatoId id = contatoAtualizado.getId();

        // 1️⃣ Busca o contato existente
        PessoaContato contatoExistente = pessoaContatoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        // 2️⃣ Atualiza SOMENTE os campos permitidos
        contatoExistente.setTipoContato(contatoAtualizado.getTipoContato());
        contatoExistente.setContato(contatoAtualizado.getContato());
        contatoExistente.setObservacao(contatoAtualizado.getObservacao());

        // 3️⃣ Pessoa já está vinculada (não mexe!)
        return pessoaContatoRepository.save(contatoExistente);
    }

    /**
     * Deletar contato
     */
    public void deletarContato(PessoaContatoId id) {
        PessoaContato contato = pessoaContatoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        pessoaContatoRepository.delete(contato);
    }

    public PessoaContato criar(PessoaContatoDTO dto) {

        Pessoa pessoa = pessoaRepository
                .findById(dto.getIdPessoa())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        Integer proximoSeq = pessoaContatoRepository
                .buscarProximoSeq(dto.getIdPessoa());

        PessoaContato contato = new PessoaContato();
        contato.setId(new PessoaContatoId(dto.getIdPessoa(), proximoSeq));
        contato.setPessoa(pessoa);

        TipoContato tipo = new TipoContato();
        tipo.setId(dto.getIdTipoContato());
        contato.setTipoContato(tipo);

        contato.setContato(dto.getContato());
        contato.setObservacao(dto.getObservacao());

        return pessoaContatoRepository.save(contato);
    }

    public PessoaContato atualizar(
            Long idPessoa,
            Integer seqContato,
            PessoaContatoDTO dto) {

        PessoaContatoId id = new PessoaContatoId(idPessoa, seqContato);

        PessoaContato contato = pessoaContatoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        TipoContato tipo = new TipoContato();
        tipo.setId(dto.getIdTipoContato());
        contato.setTipoContato(tipo);

        contato.setContato(dto.getContato());
        contato.setObservacao(dto.getObservacao());

        return pessoaContatoRepository.save(contato);
    }

}
