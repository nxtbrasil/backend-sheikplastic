package com.sheikplastic.service;

import com.sheikplastic.dto.PessoaProdutoDTO;
import com.sheikplastic.dto.PessoaProdutoListaDTO;
import com.sheikplastic.model.PessoaProduto;
import com.sheikplastic.model.Produto;
import com.sheikplastic.repository.HistoricoPrecoRepository;
import com.sheikplastic.repository.PessoaProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PessoaProdutoService {

    private final PessoaProdutoRepository pessoaProdutoRepository;
    private final HistoricoPrecoRepository historicoPrecoRepository;

    public PessoaProdutoService(
            PessoaProdutoRepository pessoaProdutoRepository,
            HistoricoPrecoRepository historicoPrecoRepository) {
        this.pessoaProdutoRepository = pessoaProdutoRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
    }

    /*
     * =========================================================
     * LISTAGEM (TELA)
     * =========================================================
     */
    public List<PessoaProdutoDTO> listarProdutos(Long idPessoa) {
        return pessoaProdutoRepository.findProdutosDaPessoa(idPessoa);
    }

    /*
     * =========================================================
     * SALVAR / ATUALIZAR PRODUTO
     * =========================================================
     */
    @Transactional
    public void salvarProduto(
            Long idPessoa,
            Long seqProduto,
            Integer idProduto,
            String complemento,
            String unp,
            String unv,
            BigDecimal valorVenda,
            BigDecimal valorVendaAnterior) {

        boolean novo = (seqProduto == null || seqProduto == 0);

        if (novo) {
            seqProduto = pessoaProdutoRepository.proximoSeqProduto(idPessoa);

            pessoaProdutoRepository.inserirProduto(
                    idPessoa,
                    seqProduto,
                    idProduto,
                    complemento,
                    unp,
                    unv,
                    valorVenda);
        } else {
            pessoaProdutoRepository.atualizarProduto(
                    idPessoa,
                    seqProduto,
                    idProduto,
                    complemento,
                    unp,
                    unv,
                    valorVenda);
        }

        aplicarHistorico(idPessoa, seqProduto, valorVendaAnterior, valorVenda);
    }

    /*
     * =========================================================
     * EXCLUIR PRODUTO
     * =========================================================
     */
    @Transactional
    public void deletarProduto(Long idPessoa, Long seqProduto) {
        pessoaProdutoRepository.deletarProduto(idPessoa, seqProduto);
    }

    /*
     * =========================================================
     * DETALHE DO PRODUTO
     * =========================================================
     */
public PessoaProdutoDTO buscarDetalheProduto(
        Long idPessoa,
        Long idProduto,
        Long seqProduto) {

    return pessoaProdutoRepository.buscarDetalheProduto(
            idProduto,   // :idProduto
            seqProduto,  // :seqProduto
            idPessoa     // :idPessoa
    ).orElseThrow(() ->
        new EntityNotFoundException("Produto não encontrado para a pessoa")
    );
}

    /*
     * =========================================================
     * 🔥 PRECIFICAÇÃO EM LOTE (IGUAL AO ASP)
     * =========================================================
     */
    @Transactional
    public void atualizarValorProduto(
            Long idPessoa,
            Integer idProduto,
            BigDecimal novoValor) {

        List<PessoaProduto> produtos = pessoaProdutoRepository.buscarParaPrecificacao(idPessoa, idProduto);

        if (produtos.isEmpty()) {
            throw new EntityNotFoundException(
                    "Nenhum produto encontrado para a pessoa e produto informados.");
        }

        for (PessoaProduto pp : produtos) {
            BigDecimal valorAnterior = pp.getValorVenda();

            // UPDATE
            pessoaProdutoRepository.atualizarProduto(
                    idPessoa,
                    pp.getSeqProduto(),
                    idProduto,
                    pp.getComplementoProduto(),
                    pp.getUnpProduto(),
                    pp.getUnvProduto(),
                    novoValor);

            // HISTÓRICO
            aplicarHistorico(
                    idPessoa,
                    pp.getSeqProduto(),
                    valorAnterior,
                    novoValor);
        }
    }

    /*
     * =========================================================
     * HISTÓRICO (IGUAL AO ASP CLÁSSICO)
     * =========================================================
     */
    private void aplicarHistorico(
            Long idPessoa,
            Long seqProduto,
            BigDecimal valorAnterior,
            BigDecimal novoValor) {

        if (valorAnterior != null
                && valorAnterior.compareTo(BigDecimal.ZERO) > 0
                && novoValor.compareTo(valorAnterior) != 0) {

            historicoPrecoRepository.fecharPrecoAnterior(
                    idPessoa,
                    seqProduto,
                    valorAnterior);
        }

        if (valorAnterior == null
                || novoValor.compareTo(valorAnterior) != 0) {

            historicoPrecoRepository.inserirHistorico(
                    idPessoa,
                    seqProduto,
                    novoValor);
        }
    }

    public List<PessoaProdutoListaDTO> listarProdutosDaPessoaPorProduto(
            Long idPessoa,
            Long idProduto) {

        return pessoaProdutoRepository
                .listarPorPessoaEProduto(idPessoa, idProduto);
    }

    public List<PessoaProdutoDTO> listarProdutosDaPessoa(Long idPessoa) {
        return pessoaProdutoRepository.findProdutosDaPessoa(idPessoa);
    }

public List<Produto> listarParaCombo() {
    return pessoaProdutoRepository.listarProdutosParaCombo();
}

}
