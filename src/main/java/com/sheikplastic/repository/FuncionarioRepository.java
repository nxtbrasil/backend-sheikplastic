package com.sheikplastic.repository;

import com.sheikplastic.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByEmailFuncionarioAndAtivoTrue(String email);
    Optional<Funcionario> findByEmailFuncionario(String emailFuncionario);
    boolean existsByEmailFuncionario(String emailFuncionario);
}


