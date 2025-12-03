package com.sheikplastic.service;

import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.PessoaContatoId;
import com.sheikplastic.repository.PessoaContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoPessoaService {

    @Autowired
    private PessoaContatoRepository pessoaContatoRepository;

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
        return pessoaContatoRepository.save(contato);
    }

    /**
     * Atualizar contato
     */
    public PessoaContato atualizarContato(PessoaContato contato) {
        return pessoaContatoRepository.save(contato);
    }

    /**
     * Deletar contato
     */
    public void deletarContato(PessoaContatoId id) {
        pessoaContatoRepository.deleteById(id);
    }
}
