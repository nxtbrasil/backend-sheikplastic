package com.sheikplastic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sheikplastic.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}
