package com.sheikplastic.service;

import com.sheikplastic.model.Pessoa;
import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.PessoaContatoId;
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

    /**
     * Atualizar contato
     */
public PessoaContato atualizarContato(PessoaContato contato) {

    // Recupera a Pessoa pelo id
    Pessoa pessoa = pessoaRepository
            .findById(contato.getId().getIdPessoa())
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    // Preenche a propriedade obrigatória
    contato.setPessoa(pessoa);

    return pessoaContatoRepository.save(contato);
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
}
