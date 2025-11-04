package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuarioFuncionario;
import com.sheikplastic.model.GrupoUsuarioFuncionarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoUsuarioFuncionarioRepository
        extends JpaRepository<GrupoUsuarioFuncionario, GrupoUsuarioFuncionarioId> {
    List<GrupoUsuarioFuncionario> findByFuncionario_IdFuncionario(Long idFuncionario);

    List<GrupoUsuarioFuncionario> findById_IdFuncionario(Long idFuncionario);

    void deleteById_IdFuncionario(Long idFuncionario);

    long countById_IdFuncionarioAndId_IdGrupoUsuario(Long idFuncionario, Integer idGrupoUsuario);

    @Modifying
    @Query("INSERT INTO GrupoUsuarioFuncionario (id.idGrupoUsuario, id.idFuncionario) VALUES (:idGrupoUsuario, :idFuncionario)")
    void vincularFuncionarioAoGrupo(@Param("idGrupoUsuario") Integer idGrupoUsuario,
            @Param("idFuncionario") Long idFuncionario);

    @Modifying
    @Query("DELETE FROM GrupoUsuarioFuncionario g WHERE g.id.idGrupoUsuario = :idGrupoUsuario AND g.id.idFuncionario = :idFuncionario")
    void desvincularFuncionarioDoGrupo(@Param("idGrupoUsuario") Integer idGrupoUsuario,
            @Param("idFuncionario") Long idFuncionario);
}