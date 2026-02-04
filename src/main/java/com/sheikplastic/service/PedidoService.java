package com.sheikplastic.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sheikplastic.dto.PedidoImpressaoContatoDTO;
import com.sheikplastic.dto.PedidoImpressaoDTO;
import com.sheikplastic.dto.PedidoListDTO;
import com.sheikplastic.dto.PedidoListViewDTO;
import com.sheikplastic.dto.PedidoSaveDTO;
import com.sheikplastic.model.Pedido;
import com.sheikplastic.model.StatusPedido;
import com.sheikplastic.repository.CondicaoPagamentoRepository;
import com.sheikplastic.repository.PedidoItemRepository;
import com.sheikplastic.repository.PedidoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    private final CondicaoPagamentoRepository repository;

    public static final int ENTREGA_ACRESCIMO = 5; // igual Application("ENTREGA_ACRESCIMO")

    // ======================================================
    // LISTAGEM
    // ======================================================
    public List<PedidoListViewDTO> listarPedidos(
            Long idPedido,
            Long idPessoa,
            Long idFuncionario,
            Integer statusPedido,
            Boolean ativo,
            LocalDateTime dtEntradaIni,
            LocalDateTime dtEntradaFim,
            LocalDateTime dtEntregaIni,
            LocalDateTime dtEntregaFim,
            String ordenacao) {

        int statusFiltro = statusPedido == null ? 2 : statusPedido;

        String campoOrdenacao = (ordenacao == null || ordenacao.isEmpty())
                ? "idPedido"
                : ordenacao;

        List<PedidoListDTO> pedidos = pedidoRepository.listarPedidos(
                idPedido,
                idPessoa,
                idFuncionario,
                statusFiltro,
                ativo,
                dtEntradaIni,
                dtEntradaFim,
                dtEntregaIni,
                dtEntregaFim,
                campoOrdenacao);

        return pedidos.stream()
                .map(this::converterParaViewDTO)
                .toList();
    }

    private PedidoListViewDTO converterParaViewDTO(PedidoListDTO p) {

        StatusPedido status = calcularStatus(p);

        return PedidoListViewDTO.builder()
                .idPedido(p.getIdPedido())
                .nomePessoa(p.getNomePessoa())
                .nomeFuncionario(p.getNomeFuncionario())
                .idPessoa(p.getIdPessoa())
                .dtEntradaPedido(p.getDtEntradaPedido())
                .dtPrevisaoPedido(p.getDtPrevisaoPedido())
                .dtEntregaPedido(p.getDtEntregaPedido())
                .urgente(p.getUrgente())
                .entregue(p.getEntregue())
                .ativo(p.getAtivo())
                .qtdItens(p.getQtdItens())
                .qtdEntregue(p.getQtdEntregue())
                .valorPedido(p.getValorPedido())
                .temItensEntregues(p.getTemItensEntregues())
                .status(status)
                .build();
    }

    private StatusPedido calcularStatus(PedidoListDTO p) {

        if (p.getQtdItens() == null || p.getQtdItens() == 0) {
            return StatusPedido.NOVO;
        }

        if (Boolean.TRUE.equals(p.getEntregue())) {
            return StatusPedido.ENTREGUE;
        }

        if (p.getDtPrevisaoPedido() != null &&
                !p.getDtPrevisaoPedido().isBefore(LocalDate.now())) {
            return StatusPedido.EM_ANDAMENTO;
        }

        return StatusPedido.ATRASADO;
    }

    // ======================================================
    // CRUD (INSERT / UPDATE)
    // ======================================================
    @Transactional
    public Pedido salvarOuEditar(PedidoSaveDTO dto) {

        // ===== INCLUSÃO =====
        if (dto.getIdPedido() == null || dto.getIdPedido() == 0) {

            pedidoRepository.inserirPedido(
                    dto.getIdPessoa(),
                    dto.getIdFuncionario(),
                    dto.getIdCondicaoPagamento(),
                    dto.getDtEntradaPedido(),
                    dto.getDtPrevisaoPedido(),
                    dto.getDtEntregaPedido(),
                    dto.getNomeSolicitante(),
                    dto.getUrgente(),
                    dto.getObservacao(),
                    dto.getAtivo());

            // 🚨 NÃO FAÇA findById aqui
            return null; // ou lance um DTO de sucesso
        }

        // ===== ALTERAÇÃO =====
        pedidoRepository.atualizarPedido(
                dto.getIdPedido(),
                dto.getIdPessoa(),
                dto.getIdFuncionario(),
                dto.getIdCondicaoPagamento(),
                dto.getDtEntradaPedido(),
                dto.getDtPrevisaoPedido(),
                dto.getDtEntregaPedido(),
                dto.getNomeSolicitante(),
                dto.getUrgente(),
                dto.getObservacao(),
                dto.getEntregue(),
                dto.getAtivo());

        return pedidoRepository.findById(dto.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    // ======================================================
    // CRUD AUXILIAR
    // ======================================================
    public Pedido buscarPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public void excluir(Long idPedido) {
        pedidoRepository.deleteById(idPedido);
    }

    public PedidoImpressaoDTO buscarCabecalhoImpressao(Long idPedido) {

        PedidoImpressaoDTO dto = pedidoRepository.buscarPedidoImpressao(idPedido);

        dto.setStatusPedido(calcularStatus(dto));

        List<PedidoImpressaoContatoDTO> contatos = pedidoRepository.listarContatosPessoa(dto.getIdPessoa());

        dto.setEndereco(
                pedidoRepository.buscarEnderecoImpressao(dto.getIdPessoa()));

        dto.setContatos(contatos);

        // 🔹 CONDIÇÃO DE PAGAMENTO (via repository existente)
        if (dto.getIdPessoa() != null) {
            repository.findById(dto.getIdPessoa().intValue())
                    .ifPresent(condicao -> dto.setDescricaoCondicaoPagamento(
                            condicao.getDescricaoCondicaoPagamento()));
        }

        return dto;
    }

    private StatusPedido calcularStatus(PedidoImpressaoDTO p) {

        System.out.println("======================================");
        System.out.println("CALCULANDO STATUS DO PEDIDO");
        System.out.println("Pedido ID        : " + p.getIdPedido());
        System.out.println("Qtd Total Itens  : " + p.getQtdTotal());
        System.out.println("Flag ENTREGUE BD : " + p.getEntregue());
        System.out.println("Dt Previsão      : " + p.getDtPrevisaoPedido());
        System.out.println("Hoje             : " + LocalDate.now());

        // =========================
        // 1️⃣ NOVO
        // =========================
        if (p.getQtdTotal() == null || p.getQtdTotal().compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("➡ Status: NOVO (sem itens)");
            return StatusPedido.NOVO;
        }

        // =========================
        // 2️⃣ ENTREGUE
        // =========================
        if (Boolean.TRUE.equals(p.getEntregue())) {
            System.out.println("✔ Status: ENTREGUE (campo entregue = true)");
            return StatusPedido.ENTREGUE;
        }

        // =========================
        // 3️⃣ EM ANDAMENTO
        // =========================
        if (p.getDtPrevisaoPedido() != null &&
                !p.getDtPrevisaoPedido().isBefore(LocalDate.now())) {

            System.out.println("⏳ Status: EM ANDAMENTO (dentro do prazo)");
            return StatusPedido.EM_ANDAMENTO;
        }

        // =========================
        // 4️⃣ ATRASADO
        // =========================
        System.out.println("⚠ Status: ATRASADO (fora do prazo)");
        return StatusPedido.ATRASADO;
    }

    @Transactional
    public void excluirPedidosEmLote(List<Long> idsPedido) {

        if (idsPedido == null || idsPedido.isEmpty()) {
            return;
        }

        // 1️⃣ deleta TODOS os itens dos pedidos
        pedidoItemRepository.deletarPorPedidos(idsPedido);

        // 2️⃣ deleta os pedidos
        pedidoRepository.deletarEmLote(idsPedido);
    }

}
