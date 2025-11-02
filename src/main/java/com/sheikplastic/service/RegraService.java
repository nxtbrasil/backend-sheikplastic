package com.sheikplastic.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sheikplastic.model.Regra;
import com.sheikplastic.repository.RegraRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegraService {

    private final JdbcTemplate jdbcTemplate;

      @Autowired
    private RegraRepository regraRepository;

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

    
    public List<Regra> listarTodas() {
        return regraRepository.findAll();
    }

    public Regra buscarPorId(Long id) {
        return regraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regra não encontrada"));
    }

    public Regra salvar(Regra regra) {
        return regraRepository.save(regra);
    }

    public Regra atualizar(Long id, Regra regraAtualizada) {
        Regra regra = buscarPorId(id);
        regra.setChaveRegra(regraAtualizada.getChaveRegra());
        regra.setDescricaoRegra(regraAtualizada.getDescricaoRegra());
        return regraRepository.save(regra);
    }

    public void deletar(Long id) {
        regraRepository.deleteById(id);
    }
}