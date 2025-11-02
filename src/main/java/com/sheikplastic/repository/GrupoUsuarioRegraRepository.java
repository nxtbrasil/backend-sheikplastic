package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuarioRegra;
import com.sheikplastic.model.GrupoUsuarioHeranca;
import com.sheikplastic.model.Regra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GrupoUsuarioRegraRepository extends JpaRepository<GrupoUsuarioRegra, Integer> {
    @Query("SELECT gur.regra FROM GrupoUsuarioRegra gur WHERE gur.grupoUsuario.idGrupoUsuario = :idGrupo")
    List<Regra> findRegrasByGrupo(Integer idGrupo);
       @Query("SELECT h.grupoPai.idGrupoUsuario FROM GrupoUsuarioHeranca h WHERE h.grupoFilho.idGrupoUsuario = :idFilho")
    List<Integer> findGruposPai(Integer idFilho);
}