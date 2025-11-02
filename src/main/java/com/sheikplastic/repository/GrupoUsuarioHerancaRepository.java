package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuarioHeranca;
import com.sheikplastic.model.GrupoUsuarioHerancaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface GrupoUsuarioHerancaRepository extends JpaRepository<GrupoUsuarioHeranca, GrupoUsuarioHerancaId> {

    // Busca por grupo pai — via campo embutido (EmbeddedId)
    List<GrupoUsuarioHeranca> findById_IdGrupoPai(Integer idGrupoPai);

    // Retorna os IDs dos grupos pai de um grupo filho
    @Query("SELECT h.id.idGrupoPai FROM GrupoUsuarioHeranca h WHERE h.id.idGrupoFilho = :idFilho")
    List<Integer> findGruposPai(@Param("idFilho") Integer idFilho);
}
