package com.sheikplastic.service;

import com.sheikplastic.model.GrupoUsuarioHeranca;
import com.sheikplastic.model.GrupoUsuarioFuncionario;
import com.sheikplastic.repository.GrupoUsuarioFuncionarioRepository;
import com.sheikplastic.repository.GrupoUsuarioHerancaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GrupoService {

    private final GrupoUsuarioFuncionarioRepository gufRepo;
    private final GrupoUsuarioHerancaRepository herancaRepo;

    public GrupoService(GrupoUsuarioFuncionarioRepository gufRepo, GrupoUsuarioHerancaRepository herancaRepo) {
        this.gufRepo = gufRepo;
        this.herancaRepo = herancaRepo;
    }

    public Set<Integer> getGruposDoFuncionario(Long idFuncionario) {
        Set<Integer> grupos = new HashSet<>();
        List<GrupoUsuarioFuncionario> gufs = gufRepo.findByFuncionarioIdFuncionario(idFuncionario);
        for (GrupoUsuarioFuncionario g : gufs) {
            grupos.add(g.getGrupoUsuario().getIdGrupoUsuario());
            collectFilhos(g.getGrupoUsuario().getIdGrupoUsuario(), grupos);
        }
        return grupos;
    }

    private void collectFilhos(Integer idPai, Set<Integer> grupos) {
        List<GrupoUsuarioHeranca> filhos = herancaRepo.findByIdIdGrupoPai(idPai);
        for (GrupoUsuarioHeranca h : filhos) {
            Integer idFilho = h.getGrupoFilho().getIdGrupoUsuario();
            if (!grupos.contains(idFilho)) {
                grupos.add(idFilho);
                collectFilhos(idFilho, grupos);
            }
        }
    }
}
