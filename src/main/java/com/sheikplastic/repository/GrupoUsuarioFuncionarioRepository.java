package com.sheikplastic.repository;

import com.sheikplastic.model.GrupoUsuarioFuncionario;
import com.sheikplastic.model.GrupoUsuarioFuncionarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GrupoUsuarioFuncionarioRepository extends JpaRepository<GrupoUsuarioFuncionario, GrupoUsuarioFuncionarioId> {
    List<GrupoUsuarioFuncionario> findByFuncionario_IdFuncionario(Long idFuncionario);
}