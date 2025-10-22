package com.sheikplastic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegraService {

    private final JdbcTemplate jdbcTemplate;

    public Set<String> getRegrasFuncionario(Long idFuncionario) {
        String sql = """
            SELECT DISTINCT r.chaveRegra
            FROM GrupoUsuarioFuncionario fg
            JOIN GrupoUsuarioRegra gr ON gr.idGrupoUsuario = fg.idGrupoUsuario
            JOIN Regra r ON r.idRegra = gr.idRegra
            WHERE fg.idFuncionario = ?
        """;
        return new HashSet<>(jdbcTemplate.queryForList(sql, String.class, idFuncionario));
    }
}