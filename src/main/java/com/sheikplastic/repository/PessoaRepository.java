package com.sheikplastic.repository;

import com.sheikplastic.model.Pessoa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("""
        SELECT DISTINCT p
        FROM Pessoa p
        LEFT JOIN FETCH p.contatos c
        LEFT JOIN FETCH c.tipoContato
        """)
    List<Pessoa> findAllWithContatos();

    @Query("""
        SELECT p
        FROM Pessoa p
        LEFT JOIN FETCH p.contatos c
        LEFT JOIN FETCH c.tipoContato
        WHERE p.id = :id
        """)
    Optional<Pessoa> findByIdWithContatos(Long id);
}
