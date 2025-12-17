package com.sheikplastic.controller;

import com.sheikplastic.dto.HistoricoPrecoDTO;
import com.sheikplastic.dto.PessoaProdutoDTO;
import com.sheikplastic.dto.PessoaProdutoRequest;
import com.sheikplastic.service.HistoricoPrecoService;
import com.sheikplastic.service.PessoaProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas-produtos/{idPessoa}/produtos")
public class PessoaProdutoController {

    private final PessoaProdutoService service;
    private final HistoricoPrecoService historicoService;

    public PessoaProdutoController(
            PessoaProdutoService service,
            HistoricoPrecoService historicoService) {
        this.service = service;
        this.historicoService = historicoService;
    }

    @GetMapping
    public List<PessoaProdutoDTO> listar(@PathVariable Long idPessoa) {
        return service.listarProdutos(idPessoa);
    }

    @GetMapping("/{seqProduto}/historico")
    public List<HistoricoPrecoDTO> buscarHistorico(
        @PathVariable Long idPessoa,
        @PathVariable Long seqProduto
    ) {
        return historicoService.buscarHistorico(idPessoa, seqProduto);
    }

    // INSERT + UPDATE
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody PessoaProdutoRequest req) {

        service.salvarProduto(
            req.getIdPessoa(),
            req.getSeqProduto(),
            req.getIdProduto(),
            req.getComplementoProduto(),
            req.getUnpProduto(),
            req.getUnvProduto(),
            req.getValorVenda(),
            req.getValorVendaAnterior()
        );

        return ResponseEntity.ok().build();
    }

    // DELETE
    @DeleteMapping("/{seqProduto}")
    public ResponseEntity<Void> deletar(
        @PathVariable Long idPessoa,
        @PathVariable Long seqProduto
    ) {
        service.deletarProduto(idPessoa, seqProduto);
        return ResponseEntity.noContent().build();
    }

      @GetMapping("/{seqProduto}")
    public ResponseEntity<PessoaProdutoDTO> buscarDetalhe(
        @PathVariable Long idPessoa,
        @PathVariable Long seqProduto
    ) {
        return ResponseEntity.ok(
            service.buscarDetalheProduto(idPessoa, seqProduto)
        );
    }
}
