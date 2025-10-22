package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuarioRegra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoUsuarioRegraRepository extends JpaRepository<GrupoUsuarioRegra, Long> {
    List<GrupoUsuarioRegra> findByIdGrupoUsuario(Integer idGrupoUsuario);
}
