package com.sheikplastic.service;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sheikplastic.dto.PedidoItemDTO;
import com.sheikplastic.dto.PedidoItemView;
import com.sheikplastic.dto.PedidoResumoDTO;
import com.sheikplastic.dto.ProdutoPessoaDTO;
import com.sheikplastic.model.PedidoItem;
import com.sheikplastic.model.Produto;
import com.sheikplastic.model.StatusPedido;
import com.sheikplastic.repository.PedidoItemRepository;
import com.sheikplastic.repository.PedidoRepository;
import com.sheikplastic.repository.ProdutoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoItemService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final JdbcTemplate jdbc;

    @Autowired
private ProdutoRepository produtoRepository;

    public Map<String, Object> listarItensPedido(Long idPedido) {

        PedidoResumoDTO resumo = null;

        try {
            resumo = pedidoRepository.buscarResumoPedido(idPedido);
        } catch (Exception e) {
            // TODO: handle exception
        }

        List<PedidoItemView> itens = pedidoItemRepository.listarItensPedido(idPedido);

        Map<String, Object> response = new HashMap<>();
        response.put("pedido", resumo);
        response.put("itens", itens);

        return response;
    }

    @Transactional
    public void deletar(Long idPedido, Long idPessoa, Long seqProduto) {

        PedidoItem item = pedidoItemRepository
                .findByIdPedidoAndIdPessoaAndSeqProduto(idPedido, idPessoa, seqProduto)
                .orElseThrow(() -> new RuntimeException("Item não encontrado no pedido"));

        pedidoItemRepository.delete(item);
    }

    /*
     * =========================
     * PRODUTOS DA PESSOA (ASP)
     * =========================
     */
    public List<ProdutoPessoaDTO> listarProdutosDisponiveis(
            Long idPessoa,
            Long idPedido) {

        String sql = """
                    SELECT ppr.seqProduto,
                           prd.nomeProduto,
                           ppr.complementoProduto,
                           ppr.valorVenda,
                           ppr.unpProduto,
                           ppr.unvProduto
                    FROM PessoaProduto ppr
                    JOIN Produto prd ON prd.idProduto = ppr.idProduto
                    WHERE ppr.idPessoa = ?
                      AND NOT EXISTS (
                        SELECT 1 FROM PedidoItem pit
                        WHERE pit.idPedido = ?
                          AND pit.idPessoa = ppr.idPessoa
                          AND pit.seqProduto = ppr.seqProduto
                      )
                    ORDER BY prd.nomeProduto, ppr.complementoProduto
                """;

        return jdbc.query(sql,
                new BeanPropertyRowMapper<>(ProdutoPessoaDTO.class),
                idPessoa, idPedido);
    }

    /*
     * =========================
     * BUSCAR ITEM (ATU)
     * =========================
     */
    public PedidoItemDTO buscarItem(
            Long idPedido,
            Long idPessoa,
            Long seqProduto) {

        PedidoItem item = pedidoItemRepository
                .findByIdPedidoAndIdPessoaAndSeqProduto(
                        idPedido, idPessoa, seqProduto)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        return mapToDTO(item);
    }

    /*
     * =========================
     * INSERIR
     * =========================
     */
    public void inserir(PedidoItemDTO dto) {

        // 1️⃣ Regra: não permitir produto duplicado no pedido
        if (pedidoItemRepository.existsByIdPedidoAndIdPessoaAndSeqProduto(
                dto.getIdPedido(), dto.getIdPessoa(), dto.getSeqProduto())) {
            throw new RuntimeException("Produto já inserido no pedido");
        }

        // 2️⃣ Salva o item
        pedidoItemRepository.save(mapToEntity(dto));

        // 3️⃣ REGRA ASP: ao inserir item, pedido volta para ANDAMENTO
        pedidoRepository.findById(dto.getIdPedido()).ifPresent(pedido -> {
            pedido.setEntregue(false); // em andamento
            pedido.setAtivo(true); // garante que está ativo
            pedidoRepository.save(pedido);
        });
    }

    /*
     * =========================
     * ATUALIZAR
     * =========================
     */
    public void atualizar(PedidoItemDTO dto) {

        PedidoItem item = pedidoItemRepository
                .findByIdPedidoAndIdPessoaAndSeqProduto(
                        dto.getIdPedido(), dto.getIdPessoa(), dto.getSeqProduto())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        item.setValorVenda(dto.getValorVenda());
        item.setQtdItem(dto.getQtdItem());
        item.setUnpItem(dto.getUnpItem());
        item.setQtdEntregue(dto.getQtdEntregue());
        item.setUnvItem(dto.getUnvItem());

        pedidoItemRepository.save(item);
    }

    /*
     * =========================
     * MAPPERS
     * =========================
     */
    private PedidoItem mapToEntity(PedidoItemDTO dto) {
        PedidoItem p = new PedidoItem();
        p.setIdPedido(dto.getIdPedido());
        p.setIdPessoa(dto.getIdPessoa());
        p.setSeqProduto(dto.getSeqProduto());
        p.setValorVenda(dto.getValorVenda());
        p.setQtdItem(dto.getQtdItem());
        p.setUnpItem(dto.getUnpItem());
        p.setQtdEntregue(dto.getQtdEntregue());
        p.setUnvItem(dto.getUnvItem());
        return p;
    }

    private PedidoItemDTO mapToDTO(PedidoItem p) {
        PedidoItemDTO dto = new PedidoItemDTO();
        dto.setIdPedido(p.getIdPedido());
        dto.setIdPessoa(p.getIdPessoa());
        dto.setSeqProduto(p.getSeqProduto());
        dto.setValorVenda(p.getValorVenda());
        dto.setQtdItem(p.getQtdItem());
        dto.setUnpItem(p.getUnpItem());
        dto.setQtdEntregue(p.getQtdEntregue());
        dto.setUnvItem(p.getUnvItem());
        return dto;
    }

    public List<PedidoItemDTO> listarItensImpressao(Long idPedido) {
    return pedidoItemRepository.listarItensImpressao(idPedido);
}

}
