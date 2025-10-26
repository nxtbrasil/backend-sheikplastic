package com.sheikplastic.repository;

import com.sheikplastic.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByEmailFuncionarioAndAtivoTrue(String email);
        Optional<Funcionario> findByEmailFuncionario(String emailFuncionario);
}
