package com.sheikplastic.service;

import com.sheikplastic.model.Regra;
import com.sheikplastic.repository.GrupoUsuarioHerancaRepository;
import com.sheikplastic.repository.GrupoUsuarioRegraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupoUsuarioRegraService {

    @Autowired
    private GrupoUsuarioRegraRepository grupoUsuarioRegraRepository;

    @Autowired
    private GrupoUsuarioHerancaRepository grupoUsuarioHerancaRepository;

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
        if (visitados.contains(idGrupo)) return;
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
}
