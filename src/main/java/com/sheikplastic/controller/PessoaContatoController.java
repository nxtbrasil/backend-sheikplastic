package com.sheikplastic.controller;

import com.sheikplastic.dto.PessoaContatoDTO;
import com.sheikplastic.dto.PessoaProdutoDTO;
import com.sheikplastic.model.Pessoa;
import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.PessoaContatoId;
import com.sheikplastic.repository.PessoaContatoRepository;
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

    @Autowired
    private PessoaContatoRepository pessoaContatoRepository;

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
            @PathVariable Integer seqContato) {
        PessoaContatoId id = new PessoaContatoId(idPessoa, seqContato);
        return contatoPessoaService.buscarPorId(id);
    }

    /**
     * POST /api/pessoa-contato
     * Cria novo contato
     */
@PostMapping
public PessoaContato criarContato(@RequestBody PessoaContatoDTO dto) {
    return contatoPessoaService.criar(dto);
}


    /**
     * PUT /api/pessoa-contato/{idPessoa}/{seqContato}
     * Atualiza um contato existente
     */
@PutMapping("/{idPessoa}/{seqContato}")
public PessoaContato atualizarContato(
        @PathVariable Long idPessoa,
        @PathVariable Integer seqContato,
        @RequestBody PessoaContatoDTO dto) {

    return contatoPessoaService.atualizar(idPessoa, seqContato, dto);
}

    /**
     * DELETE /api/pessoa-contato/{idPessoa}/{seqContato}
     * Deleta contato
     */
    @DeleteMapping("/{idPessoa}/{seqContato}")
    public void deletarContato(
            @PathVariable Long idPessoa,
            @PathVariable Integer seqContato) {
        PessoaContatoId id = new PessoaContatoId(idPessoa, seqContato);
        contatoPessoaService.deletarContato(id);
    }

}
