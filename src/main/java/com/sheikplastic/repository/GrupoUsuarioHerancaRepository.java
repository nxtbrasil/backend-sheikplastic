package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuarioHeranca;
import com.sheikplastic.model.GrupoUsuarioHerancaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GrupoUsuarioHerancaRepository extends JpaRepository<GrupoUsuarioHeranca, GrupoUsuarioHerancaId> {

    List<GrupoUsuarioHeranca> findById_IdGrupoPai(Integer idGrupoPai);

    @Query("SELECT h.id.idGrupoPai FROM GrupoUsuarioHeranca h WHERE h.id.idGrupoFilho = :idFilho")
    List<Integer> findGruposPai(@Param("idFilho") Integer idFilho);

    @Modifying
    @Transactional
    void deleteById_IdGrupoPaiAndId_IdGrupoFilho(Integer idGrupoPai, Integer idGrupoFilho);

    boolean existsById_IdGrupoPaiAndId_IdGrupoFilho(Integer idGrupoPai, Integer idGrupoFilho);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END " +
           "FROM GrupoUsuarioHeranca h " +
           "WHERE h.id.idGrupoPai = :idGrupoPai AND h.id.idGrupoFilho = :idGrupoFilho")
    boolean isVinculado(@Param("idGrupoPai") Integer idGrupoPai,
                        @Param("idGrupoFilho") Integer idGrupoFilho);
}
