package com.sheikplastic.repository;

import com.sheikplastic.model.Cidade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

        List<Cidade> findByEstado_IdEstado(Long idEstado);

}
