package com.sheikplastic.controller;

import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.PessoaContatoId;
import com.sheikplastic.service.ContatoPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoa-contato")
public class PessoaContatoController {

    @Autowired
    private ContatoPessoaService contatoPessoaService;

    /**
     * GET /api/pessoa-contato/{idPessoa}
     * Lista todos os contatos da pessoa
     */
    @GetMapping("/{idPessoa}")
    public List<PessoaContato> listarPorPessoa(@PathVariable Long idPessoa) {
        return contatoPessoaService.listarPorPessoa(idPessoa);
    }

    /**
     * GET /api/pessoa-contato/{idPessoa}/{seqContato}
     * Busca contato pelo ID composto
     */
    @GetMapping("/{idPessoa}/{seqContato}")
    public Optional<PessoaContato> buscarContato(
            @PathVariable Long idPessoa,
            @PathVariable Integer seqContato
    ) {
        PessoaContatoId id = new PessoaContatoId(idPessoa, seqContato);
        return contatoPessoaService.buscarPorId(id);
    }

    /**
     * POST /api/pessoa-contato
     * Cria novo contato
     */
    @PostMapping
    public PessoaContato criarContato(@RequestBody PessoaContato contato) {
        return contatoPessoaService.salvar(contato);
    }

    /**
     * PUT /api/pessoa-contato/{idPessoa}/{seqContato}
     * Atualiza um contato existente
     */
    @PutMapping("/{idPessoa}/{seqContato}")
    public PessoaContato atualizarContato(
            @PathVariable Long idPessoa,
            @PathVariable Integer seqContato,
            @RequestBody PessoaContato contatoAtualizado
    ) {
        // Garantir que o ID no body é o mesmo da URL
        PessoaContatoId id = new PessoaContatoId(idPessoa, seqContato);
        contatoAtualizado.setId(id);
        return contatoPessoaService.atualizarContato(contatoAtualizado);
    }

    /**
     * DELETE /api/pessoa-contato/{idPessoa}/{seqContato}
     * Deleta contato
     */
    @DeleteMapping("/{idPessoa}/{seqContato}")
    public void deletarContato(
            @PathVariable Long idPessoa,
            @PathVariable Integer seqContato
    ) {
        PessoaContatoId id = new PessoaContatoId(idPessoa, seqContato);
        contatoPessoaService.deletarContato(id);
    }
}
