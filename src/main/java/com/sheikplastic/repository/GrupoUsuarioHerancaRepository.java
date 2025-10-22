package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuarioHeranca;
import com.sheikplastic.model.GrupoUsuarioHerancaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoUsuarioHerancaRepository extends JpaRepository<GrupoUsuarioHeranca, GrupoUsuarioHerancaId> {
    List<GrupoUsuarioHeranca> findByIdIdGrupoPai(Integer idGrupoPai);
}
