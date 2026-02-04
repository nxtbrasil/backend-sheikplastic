package com.sheikplastic.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sheikplastic.dto.PedidoImpressaoContatoDTO;
import com.sheikplastic.dto.PedidoImpressaoDTO;
import com.sheikplastic.dto.PedidoImpressaoEnderecoDTO;
import com.sheikplastic.dto.PedidoListDTO;
import com.sheikplastic.dto.PedidoResumoDTO;
import com.sheikplastic.model.Pedido;

import jakarta.transaction.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /*
     * =========================
     * LISTAGEM (NÃO ALTERADA)
     * =========================
     */
    @Query(value = """
    SELECT
        ped.idPedido            AS idPedido,
        pes.nomePessoa          AS nomePessoa,
        pes.idPessoa            AS idPessoa,
        ped.dtEntradaPedido     AS dtEntradaPedido,
        ped.dtPrevisaoPedido    AS dtPrevisaoPedido,
        ped.dtEntregaPedido     AS dtEntregaPedido,
        fnc.nomeFuncionario     AS nomeFuncionario,
        ped.urgente             AS urgente,
        ped.entregue            AS entregue,
        ped.ativo               AS ativo,

        (SELECT SUM(pit.qtdItem)
         FROM PedidoItem pit
         WHERE pit.idPedido = ped.idPedido) AS qtdItens,

        (SELECT SUM(pit.qtdEntregue)
         FROM PedidoItem pit
         WHERE pit.idPedido = ped.idPedido) AS qtdEntregue,

        (SELECT SUM(pit.qtdItem * pit.valorVenda)
         FROM PedidoItem pit
         WHERE pit.idPedido = ped.idPedido) AS valorPedido,

        CASE
            WHEN EXISTS (
                SELECT 1
                FROM PedidoItem pit
                WHERE pit.idPedido = ped.idPedido
                  AND pit.qtdEntregue > 0
            ) THEN 1
            ELSE 0
        END AS temItensEntregues

    FROM Pedido ped
    INNER JOIN Pessoa pes ON ped.idPessoa = pes.idPessoa
    INNER JOIN Funcionario fnc ON ped.idFuncionario = fnc.idFuncionario

    WHERE (:idPedido IS NULL OR ped.idPedido = :idPedido)
      AND (:idPessoa IS NULL OR ped.idPessoa = :idPessoa)
      AND (:idFuncionario IS NULL OR ped.idFuncionario = :idFuncionario)
      AND (:ativo IS NULL OR ped.ativo = :ativo)
      AND (
            :statusPedido = 2
            OR (:statusPedido = 1 AND ped.entregue = 1)
            OR (:statusPedido = -1 AND ped.entregue = 0)
      )
      AND (:dtEntradaIni IS NULL OR ped.dtEntradaPedido >= :dtEntradaIni)
      AND (:dtEntradaFim IS NULL OR ped.dtEntradaPedido <= :dtEntradaFim)
      AND (:dtEntregaIni IS NULL OR ped.dtPrevisaoPedido >= :dtEntregaIni)
      AND (:dtEntregaFim IS NULL OR ped.dtPrevisaoPedido <= :dtEntregaFim)

    ORDER BY
        CASE WHEN :ordenacao = 'idPedido' THEN ped.idPedido END DESC,
        CASE WHEN :ordenacao = 'nomePessoa' THEN pes.nomePessoa END DESC,
        CASE WHEN :ordenacao = 'dtEntradaPedido' THEN ped.dtEntradaPedido END DESC,
        CASE WHEN :ordenacao = 'dtPrevisaoPedido' THEN ped.dtPrevisaoPedido END DESC
""", nativeQuery = true)
    List<PedidoListDTO> listarPedidos(
            @Param("idPedido") Long idPedido,
            @Param("idPessoa") Long idPessoa,
            @Param("idFuncionario") Long idFuncionario,
            @Param("statusPedido") Integer statusPedido,
            @Param("ativo") Boolean ativo,
            @Param("dtEntradaIni") LocalDateTime dtEntradaIni,
            @Param("dtEntradaFim") LocalDateTime dtEntradaFim,
            @Param("dtEntregaIni") LocalDateTime dtEntregaIni,
            @Param("dtEntregaFim") LocalDateTime dtEntregaFim,
            @Param("ordenacao") String ordenacao);

    /*
     * =========================
     * INSERT (SEM RETORNO)
     * =========================
     */
    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO Pedido (
                idPessoa,
                idFuncionario,
                idCondicaoPagamento,
                dtEntradaPedido,
                dtPrevisaoPedido,
                dtEntregaPedido,
                nomeSolicitante,
                urgente,
                observacao,
                entregue,
                ativo
            )
            VALUES (
                :idPessoa,
                :idFuncionario,
                :idCondicaoPagamento,
                :dtEntrada,
                :dtPrevisao,
                :dtEntrega,
                :nomeSolicitante,
                :urgente,
                :observacao,
                0,
                :ativo
            )
            """, nativeQuery = true)
    int inserirPedido(
            @Param("idPessoa") Long idPessoa,
            @Param("idFuncionario") Long idFuncionario,
            @Param("idCondicaoPagamento") Long idCondicaoPagamento,
            @Param("dtEntrada") LocalDate dtEntrada,
            @Param("dtPrevisao") LocalDate dtPrevisao,
            @Param("dtEntrega") LocalDate dtEntrega,
            @Param("nomeSolicitante") String nomeSolicitante,
            @Param("urgente") Boolean urgente,
            @Param("observacao") String observacao,
            @Param("ativo") Boolean ativo);

    /*
     * =========================
     * UPDATE (IGUAL AO ASP)
     * =========================
     */
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE Pedido SET
                idPessoa = :idPessoa,
                idFuncionario = :idFuncionario,
                idCondicaoPagamento = :idCondicaoPagamento,
                dtEntradaPedido = :dtEntrada,
                dtPrevisaoPedido = :dtPrevisao,
                dtEntregaPedido = :dtEntrega,
                nomeSolicitante = :nomeSolicitante,
                urgente = :urgente,
                observacao = :observacao,
                entregue = :entregue,
                ativo = :ativo
            WHERE idPedido = :idPedido
            """, nativeQuery = true)
    void atualizarPedido(
            @Param("idPedido") Long idPedido,
            @Param("idPessoa") Long idPessoa,
            @Param("idFuncionario") Long idFuncionario,
            @Param("idCondicaoPagamento") Long idCondicaoPagamento,
            @Param("dtEntrada") LocalDate dtEntrada,
            @Param("dtPrevisao") LocalDate dtPrevisao,
            @Param("dtEntrega") LocalDate dtEntrega,
            @Param("nomeSolicitante") String nomeSolicitante,
            @Param("urgente") Boolean urgente,
            @Param("observacao") String observacao,
            @Param("entregue") Boolean entregue,
            @Param("ativo") Boolean ativo);

    @Query(value = """
            SELECT
                ped.idPessoa,
                pes.nomePessoa,
                ped.dtEntradaPedido,
                ped.dtPrevisaoPedido,
                ped.dtEntregaPedido,
                fnc.nomeFuncionario,
                cpg.descricaoCondicaoPagamento,
                ped.urgente,
                ped.entregue,
                (SELECT SUM(pit.qtdItem) FROM PedidoItem pit WHERE pit.idPedido = ped.idPedido) qtdItens,
                (SELECT SUM(pit.qtdItem * pit.valorVenda) FROM PedidoItem pit WHERE pit.idPedido = ped.idPedido) valorPedido,
                (SELECT SUM(pit.qtdEntregue * pit.valorVenda) FROM PedidoItem pit WHERE pit.idPedido = ped.idPedido) valorEntregue
            FROM Pedido ped
            INNER JOIN Pessoa pes ON ped.idPessoa = pes.idPessoa
            INNER JOIN Funcionario fnc ON ped.idFuncionario = fnc.idFuncionario
            INNER JOIN CondicaoPagamento cpg ON ped.idCondicaoPagamento = cpg.idCondicaoPagamento
            WHERE ped.idPedido = :idPedido
            """, nativeQuery = true)
    PedidoResumoDTO buscarResumoPedido(@Param("idPedido") Long idPedido);

    @Query("""
                SELECT new com.sheikplastic.dto.PedidoImpressaoDTO(
                    p.idPedido,
                    p.idPessoa,
                    pes.nome,
                    f.nomeFuncionario,
                    cp.descricaoCondicaoPagamento,
                    p.dtEntradaPedido,
                    p.dtPrevisaoPedido,
                    p.dtEntregaPedido,
                    p.urgente,
                    p.entregue,
                    p.observacao,

                    COALESCE((
                        SELECT SUM(i.qtdItem)
                        FROM PedidoItem i
                        WHERE i.idPedido = p.idPedido
                    ), 0L),

                    COALESCE((
                        SELECT SUM(i.qtdItem * i.valorVenda)
                        FROM PedidoItem i
                        WHERE i.idPedido = p.idPedido
                    ), 0D),

                    COALESCE((
                        SELECT SUM(i.qtdEntregue * i.valorVenda)
                        FROM PedidoItem i
                        WHERE i.idPedido = p.idPedido
                    ), 0D)
                )
                FROM Pedido p
                JOIN Pessoa pes ON pes.id = p.idPessoa
                JOIN Funcionario f ON f.idFuncionario = p.idFuncionario
                JOIN CondicaoPagamento cp ON cp.idCondicaoPagamento = p.idCondicaoPagamento
                WHERE p.idPedido = :idPedido
            """)
    PedidoImpressaoDTO buscarPedidoImpressao(@Param("idPedido") Long idPedido);

    @Query("""
                SELECT new com.sheikplastic.dto.PedidoImpressaoContatoDTO(
                    pc.pessoa.id,
                    pc.id.seqContato,
                    tc.id,
                    tc.descricao,
                    pc.contato,
                    pc.observacao
                )
                FROM PessoaContato pc
                JOIN pc.tipoContato tc
                WHERE pc.pessoa.id = :idPessoa
                ORDER BY pc.id.seqContato
            """)
    List<PedidoImpressaoContatoDTO> listarContatosPessoa(@Param("idPessoa") Long idPessoa);

    @Query("""
                SELECT new com.sheikplastic.dto.PedidoImpressaoEnderecoDTO(
                    p.logradouroPessoa,
                    p.numeroPessoa,
                    p.complementoPessoa,
                    p.bairroPessoa,
                    p.cepPessoaString,
                    c.nomeCidade,
                    e.siglaEstado
                )
                FROM Pessoa p
                JOIN p.cidade c
                JOIN c.estado e
                WHERE p.id = :idPessoa
            """)
    PedidoImpressaoEnderecoDTO buscarEnderecoImpressao(@Param("idPessoa") Long idPessoa);

        @Modifying
    @Transactional
    @Query("""
        DELETE FROM Pedido p
        WHERE p.idPedido IN :idsPedido
    """)
    void deletarEmLote(List<Long> idsPedido);

}
