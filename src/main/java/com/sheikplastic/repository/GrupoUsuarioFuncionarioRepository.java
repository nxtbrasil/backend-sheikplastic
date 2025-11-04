package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuarioFuncionario;
import com.sheikplastic.model.GrupoUsuarioFuncionarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GrupoUsuarioFuncionarioRepository extends JpaRepository<GrupoUsuarioFuncionario, GrupoUsuarioFuncionarioId> {
    List<GrupoUsuarioFuncionario> findByFuncionario_IdFuncionario(Long idFuncionario);
List<GrupoUsuarioFuncionario> findById_IdFuncionario(Long idFuncionario);
        void deleteById_IdFuncionario(Long idFuncionario);

            long countById_IdFuncionarioAndId_IdGrupoUsuario(Long idFuncionario, Integer idGrupoUsuario);

}