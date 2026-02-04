package com.sheikplastic.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sheikplastic.dto.PedidoListViewDTO;
import com.sheikplastic.dto.PedidoSaveDTO;
import com.sheikplastic.model.Pedido;
import com.sheikplastic.service.PedidoItemService;
import com.sheikplastic.service.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoItemService pedidoItemService;

    /**
     * LISTAGEM COM FILTROS
     */
    @GetMapping
    public List<PedidoListViewDTO> listar(
            @RequestParam(required = false) Long idPedido,
            @RequestParam(required = false) Long idPessoa,
            @RequestParam(required = false) Long idFuncionario,
            @RequestParam(required = false) Integer statusPedido,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dtEntradaIni,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dtEntradaFim,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dtEntregaIni,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dtEntregaFim,
            @RequestParam(required = false) String ordenacao) {
        // Converte LocalDate para LocalDateTime para o Repository cobrir o dia inteiro
        LocalDateTime entradaInicio = (dtEntradaIni != null) ? dtEntradaIni.atStartOfDay() : null;
        LocalDateTime entradaFim = (dtEntradaFim != null) ? dtEntradaFim.atTime(23, 59, 59) : null;

        LocalDateTime entregaInicio = (dtEntregaIni != null) ? dtEntregaIni.atStartOfDay() : null;
        LocalDateTime entregaFim = (dtEntregaFim != null) ? dtEntregaFim.atTime(23, 59, 59) : null;

        return pedidoService.listarPedidos(
                idPedido, idPessoa, idFuncionario, statusPedido, ativo,
                entradaInicio, entradaFim, entregaInicio, entregaFim, ordenacao);
    }

    /**
     * BUSCAR POR ID
     */
    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody PedidoSaveDTO dto) {
        dto.setIdPedido(null);
        Pedido pedido = pedidoService.salvarOuEditar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> editar(
            @PathVariable Long id,
            @RequestBody PedidoSaveDTO dto) {

        dto.setIdPedido(id);
        Pedido pedido = pedidoService.salvarOuEditar(dto);
        return ResponseEntity.ok(pedido);
    }

    /**
     * EXCLUIR
     */
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        pedidoService.excluir(id);
    }

    @GetMapping("/{idPedido}/impressao")
    public ResponseEntity<Map<String, Object>> imprimir(@PathVariable Long idPedido) {

        Map<String, Object> resp = new HashMap<>();

        resp.put("pedido", pedidoService.buscarCabecalhoImpressao(idPedido));
        resp.put("itens", pedidoItemService.listarItensImpressao(idPedido));

        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/lote")
    public ResponseEntity<Void> deletarEmLote(
            @RequestBody List<Long> idsPedido) {
        pedidoService.excluirPedidosEmLote(idsPedido);
        return ResponseEntity.noContent().build();
    }

}
