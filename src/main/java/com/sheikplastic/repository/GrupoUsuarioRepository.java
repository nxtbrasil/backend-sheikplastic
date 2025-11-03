package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Integer> {

        @Query("SELECT g FROM GrupoUsuario g WHERE g.idGrupoUsuario <> :idGrupoPai AND (:nome IS NULL OR g.nomeGrupoUsuario LIKE %:nome%) ORDER BY g.nomeGrupoUsuario")
    List<GrupoUsuario> findAllExceptAndFilter(Integer idGrupoPai, String nome);
}
