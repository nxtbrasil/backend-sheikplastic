package com.sheikplastic.service;

import com.sheikplastic.model.GrupoUsuario;
import com.sheikplastic.model.GrupoUsuarioRegra;
import com.sheikplastic.model.Regra;
import com.sheikplastic.repository.GrupoUsuarioHerancaRepository;
import com.sheikplastic.repository.GrupoUsuarioRegraRepository;
import com.sheikplastic.repository.GrupoUsuarioRepository;
import com.sheikplastic.repository.RegraRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupoUsuarioRegraService {

    @Autowired
    private GrupoUsuarioRegraRepository grupoUsuarioRegraRepository;

    @Autowired
    private GrupoUsuarioHerancaRepository grupoUsuarioHerancaRepository;
    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    @Autowired
    private RegraRepository regraRepository;

    /**
     * Retorna todas as regras diretas e herdadas de um grupo.
     */
    public List<Regra> listarRegrasComHeranca(Integer idGrupo) {
        Set<Integer> gruposVisitados = new HashSet<>();
        Set<Regra> regras = new HashSet<>();

        buscarRegrasRecursivo(idGrupo, gruposVisitados, regras);
        return new ArrayList<>(regras);
    }

    private void buscarRegrasRecursivo(Integer idGrupo, Set<Integer> visitados, Set<Regra> regras) {
        if (visitados.contains(idGrupo))
            return;
        visitados.add(idGrupo);

        // Regras diretas do grupo
        List<Regra> diretas = grupoUsuarioRegraRepository.findRegrasByGrupo(idGrupo);
        regras.addAll(diretas);

        // Buscar grupos pais (heranças)
        List<Integer> pais = grupoUsuarioHerancaRepository.findGruposPai(idGrupo);
        for (Integer idPai : pais) {
            buscarRegrasRecursivo(idPai, visitados, regras);
        }
    }

    public void vincularRegraAoGrupo(Integer grupoId, Long regraId) {
        GrupoUsuario grupo = grupoUsuarioRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        Regra regra = regraRepository.findById(regraId)
                .orElseThrow(() -> new RuntimeException("Regra não encontrada"));

        GrupoUsuarioRegra vinculo = new GrupoUsuarioRegra();
        vinculo.setGrupoUsuario(grupo);
        vinculo.setRegra(regra);

        grupoUsuarioRegraRepository.save(vinculo);
    }
      @Transactional
     public void desvincularRegraDoGrupo(Integer grupoId, Long regraId) {
        if (!grupoUsuarioRegraRepository.existsByIdIdGrupoUsuarioAndIdIdRegra(grupoId, regraId)) {
            throw new RuntimeException("Regra não vinculada a este grupo.");
        }

        grupoUsuarioRegraRepository.deleteByIdIdGrupoUsuarioAndIdIdRegra(grupoId, regraId);
    }
}
