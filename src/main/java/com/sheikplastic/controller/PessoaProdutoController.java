package com.sheikplastic.controller;

import com.sheikplastic.dto.HistoricoPrecoDTO;
import com.sheikplastic.dto.PessoaProdutoDTO;
import com.sheikplastic.dto.PessoaProdutoListaDTO;
import com.sheikplastic.dto.PessoaProdutoRequest;
import com.sheikplastic.model.Produto;
import com.sheikplastic.service.HistoricoPrecoService;
import com.sheikplastic.service.PessoaProdutoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas-produtos/{idPessoa}/produtos")
public class PessoaProdutoController {

    private final PessoaProdutoService pessoaProdutoService;
    private final HistoricoPrecoService historicoPrecoService;

    public PessoaProdutoController(
            PessoaProdutoService pessoaProdutoService,
            HistoricoPrecoService historicoPrecoService) {
        this.pessoaProdutoService = pessoaProdutoService;
        this.historicoPrecoService = historicoPrecoService;
    }

    /*
     * =========================================================
     * LISTAR PRODUTOS DA PESSOA
     * =================================================== ======
     */
    @GetMapping
    public List<PessoaProdutoDTO> listar(@PathVariable Long idPessoa) {
        return pessoaProdutoService.listarProdutos(idPessoa);
    }

    /*
     * =========================================================
     * DETALHE DO PRODUTO (seqProduto)
     * =========================================================
     */
@GetMapping("/{idProduto}/{seqProduto}")
public ResponseEntity<PessoaProdutoDTO> buscarDetalhe(
        @PathVariable("idPessoa") Long idPessoa,
        @PathVariable("idProduto") Long idProduto,
        @PathVariable("seqProduto") Long seqProduto) {

    return ResponseEntity.ok(
        pessoaProdutoService.buscarDetalheProduto(
            idPessoa,
            idProduto,
            seqProduto
        )
    );
}
    /*
     * =========================================================
     * HISTÓRICO DE PREÇO
     * =========================================================
     */
    @GetMapping("/{seqProduto}/historico")
    public List<HistoricoPrecoDTO> buscarHistorico(
            @PathVariable Long idPessoa,
            @PathVariable Long seqProduto) {

        return historicoPrecoService.buscarHistorico(idPessoa, seqProduto);
    }

    /*
     * =========================================================
     * INSERIR / EDITAR PRODUTO
     * =========================================================
     */
    @PostMapping
    public ResponseEntity<Void> salvar(
            @PathVariable Long idPessoa,
            @RequestBody PessoaProdutoRequest req) {

        pessoaProdutoService.salvarProduto(
                idPessoa,
                req.getSeqProduto(),
                req.getIdProduto(),
                req.getComplementoProduto(),
                req.getUnpProduto(),
                req.getUnvProduto(),
                req.getValorVenda(),
                req.getValorVendaAnterior());

        return ResponseEntity.ok().build();
    }

    /*
     * =========================================================
     * DELETAR PRODUTO
     * =========================================================
     */
    @DeleteMapping("/{seqProduto}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long idPessoa,
            @PathVariable Long seqProduto) {

        pessoaProdutoService.deletarProduto(idPessoa, seqProduto);
        return ResponseEntity.noContent().build();
    }

    /*
     * =========================================================
     * 🔥 ATUALIZAR PREÇO EM LOTE (IGUAL AO ASP)
     * =========================================================
     */
    @PutMapping("/{idProduto}/valor")
    public ResponseEntity<Void> atualizarValorProduto(
            @PathVariable Long idPessoa,
            @PathVariable Integer idProduto,
            @RequestParam BigDecimal novoValor) {

        pessoaProdutoService.atualizarValorProduto(
                idPessoa,
                idProduto,
                novoValor);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/produto/{idProduto}")
    public List<PessoaProdutoListaDTO> listarPorProduto(
            @PathVariable Long idPessoa,
            @PathVariable Long idProduto) {

        return pessoaProdutoService
                .listarProdutosDaPessoaPorProduto(idPessoa, idProduto);
    }



}
