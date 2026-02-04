    package com.sheikplastic.controller;

    import java.util.List;

    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import com.sheikplastic.dto.PedidoItemDTO;
    import com.sheikplastic.dto.ProdutoPessoaDTO;
    import com.sheikplastic.service.PedidoItemService;

    import lombok.RequiredArgsConstructor;

    @RestController
    @RequestMapping("/api/pedidos")
    @RequiredArgsConstructor
    public class PedidoItemController {

        private final PedidoItemService pedidoItemService;
        private final PedidoItemService service;

        @GetMapping("/{idPedido}/itens")
        public ResponseEntity<?> listarItens(@PathVariable Long idPedido) {
            return ResponseEntity.ok(
                pedidoItemService.listarItensPedido(idPedido)
            );
        }

        @GetMapping("/{pessoaId}/{pedidoId}/produtos")
        public List<ProdutoPessoaDTO> produtosDisponiveis(
            @PathVariable Long pessoaId,
            @PathVariable Long pedidoId) {

            return service.listarProdutosDisponiveis(pessoaId, pedidoId);
        }

        @GetMapping("/{pessoaId}/{pedidoId}/itens/{seqProduto}")
        public PedidoItemDTO buscarItem(
            @PathVariable Long pessoaId,
            @PathVariable Long pedidoId,
            @PathVariable Long seqProduto) {

            return service.buscarItem(pedidoId, pessoaId, seqProduto);
        }

        @PostMapping("/{pessoaId}/{pedidoId}/itens")
        public void inserir(
            @PathVariable Long pessoaId,
            @PathVariable Long pedidoId,
            @RequestBody PedidoItemDTO dto) {

            dto.setIdPessoa(pessoaId);
            dto.setIdPedido(pedidoId);
            service.inserir(dto);
        }

        @PutMapping("/{pessoaId}/{pedidoId}/itens/{seqProduto}")
        public void atualizar(
            @PathVariable Long pessoaId,
            @PathVariable Long pedidoId,
            @PathVariable Long seqProduto,
            @RequestBody PedidoItemDTO dto) {

            dto.setIdPessoa(pessoaId);
            dto.setIdPedido(pedidoId);
            dto.setSeqProduto(seqProduto);
            service.atualizar(dto);
        }

        @DeleteMapping("/{pessoaId}/{pedidoId}/itens/{seqProduto}")
    public ResponseEntity<Void> deletar(
        @PathVariable Long pessoaId,
        @PathVariable Long pedidoId,
        @PathVariable Long seqProduto) {

        service.deletar(pedidoId, pessoaId, seqProduto);
        return ResponseEntity.noContent().build();
    }

    }
