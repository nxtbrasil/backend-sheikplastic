package com.sheikplastic.service;

import com.sheikplastic.dto.GrupoUsuarioHerancaResponse;
import com.sheikplastic.model.GrupoUsuario;
import com.sheikplastic.model.GrupoUsuarioHeranca;
import com.sheikplastic.model.GrupoUsuarioHerancaId;
import com.sheikplastic.repository.GrupoUsuarioHerancaRepository;
import com.sheikplastic.repository.GrupoUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoUsuarioHerancaService {

    private final GrupoUsuarioRepository grupoUsuarioRepository;
    private final GrupoUsuarioHerancaRepository herancaRepository;

    public GrupoUsuarioHerancaService(GrupoUsuarioRepository grupoUsuarioRepository,
                                      GrupoUsuarioHerancaRepository herancaRepository) {
        this.grupoUsuarioRepository = grupoUsuarioRepository;
        this.herancaRepository = herancaRepository;
    }

    public GrupoUsuarioHerancaResponse listarGruposFilhos(Integer idGrupoPai, String filtroNome) {
        GrupoUsuario grupoPai = grupoUsuarioRepository.findById(idGrupoPai)
                .orElseThrow(() -> new RuntimeException("Grupo Pai não encontrado"));

        List<GrupoUsuarioHerancaResponse.GrupoFilhoDTO> filhos = grupoUsuarioRepository
                .findAllExceptAndFilter(idGrupoPai, filtroNome)
                .stream()
                .map(g -> new GrupoUsuarioHerancaResponse.GrupoFilhoDTO(
                        g.getIdGrupoUsuario(),
                        g.getNomeGrupoUsuario(),
                        herancaRepository.existsById_IdGrupoPaiAndId_IdGrupoFilho(idGrupoPai, g.getIdGrupoUsuario())
                ))
                .collect(Collectors.toList());

        GrupoUsuarioHerancaResponse response = new GrupoUsuarioHerancaResponse();
        response.setGrupoPai(new GrupoUsuarioHerancaResponse.GrupoPaiDTO(
                grupoPai.getIdGrupoUsuario(),
                grupoPai.getNomeGrupoUsuario()
        ));
        response.setFiltroAplicado(filtroNome);
        response.setGrupos(filhos);

        return response;
    }

@Transactional
public void vincular(Integer idGrupoPai, Integer idGrupoFilho) {
    if (!herancaRepository.existsById_IdGrupoPaiAndId_IdGrupoFilho(idGrupoPai, idGrupoFilho)) {

        GrupoUsuario grupoPai = grupoUsuarioRepository.findById(idGrupoPai)
                .orElseThrow(() -> new RuntimeException("Grupo pai não encontrado"));

        GrupoUsuario grupoFilho = grupoUsuarioRepository.findById(idGrupoFilho)
                .orElseThrow(() -> new RuntimeException("Grupo filho não encontrado"));

        GrupoUsuarioHeranca heranca = new GrupoUsuarioHeranca();
        heranca.setId(new GrupoUsuarioHerancaId(idGrupoPai, idGrupoFilho));
        heranca.setGrupoPai(grupoPai);
        heranca.setGrupoFilho(grupoFilho);

        herancaRepository.save(heranca);
    }
}

    @Transactional
    public void desvincular(Integer idGrupoPai, Integer idGrupoFilho) {
        if (herancaRepository.existsById_IdGrupoPaiAndId_IdGrupoFilho(idGrupoPai, idGrupoFilho)) {
            herancaRepository.deleteById_IdGrupoPaiAndId_IdGrupoFilho(idGrupoPai, idGrupoFilho);
        }
    }

    public boolean isVinculado(Integer idGrupoPai, Integer idGrupoFilho) {
        return herancaRepository.existsById_IdGrupoPaiAndId_IdGrupoFilho(idGrupoPai, idGrupoFilho);
    }
}
