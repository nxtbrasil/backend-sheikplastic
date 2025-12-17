package com.sheikplastic.service;

import com.sheikplastic.dto.PessoaProdutoDTO;
import com.sheikplastic.model.PessoaProduto;
import com.sheikplastic.repository.HistoricoPrecoRepository;
import com.sheikplastic.repository.PessoaProdutoRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public List<PessoaProdutoDTO> listarProdutos(Long idPessoa) {
        return pessoaProdutoRepository.findProdutosDaPessoa(idPessoa);
    }

    @Transactional
    public void salvarProduto(
        Long idPessoa,
        Long seqProduto,
        Long idProduto,
        String complemento,
        String unp,
        String unv,
        BigDecimal valorVenda,
        BigDecimal valorVendaAnterior
    ) {

        boolean isNovo = (seqProduto == null || seqProduto == 0);

        if (isNovo) {
            seqProduto = pessoaProdutoRepository.proximoSeqProduto(idPessoa);

            pessoaProdutoRepository.inserirProduto(
                idPessoa, seqProduto, idProduto, complemento, unp, unv, valorVenda
            );
        } else {
            pessoaProdutoRepository.atualizarProduto(
                idPessoa, seqProduto, idProduto, complemento, unp, unv, valorVenda
            );
        }

        // 🔥 REGRA HISTÓRICO — IGUAL AO ASP
        if (valorVendaAnterior != null
            && valorVendaAnterior.compareTo(BigDecimal.ZERO) > 0
            && valorVenda.compareTo(valorVendaAnterior) != 0) {

            historicoPrecoRepository.fecharPrecoAnterior(
                idPessoa, seqProduto, valorVendaAnterior
            );
        }

        if (valorVendaAnterior == null
            || valorVenda.compareTo(valorVendaAnterior) != 0) {

            historicoPrecoRepository.inserirHistorico(
                idPessoa, seqProduto, valorVenda
            );
        }
    }

    @Transactional
    public void deletarProduto(Long idPessoa, Long seqProduto) {
        pessoaProdutoRepository.deletarProduto(idPessoa, seqProduto);
    }

     public PessoaProdutoDTO buscarDetalheProduto(Long idPessoa, Long seqProduto) {
        return pessoaProdutoRepository.buscarDetalheProduto(idPessoa, seqProduto)
            .orElseThrow(() ->
                new RuntimeException("Produto não encontrado para a pessoa")
            );
    }
}
