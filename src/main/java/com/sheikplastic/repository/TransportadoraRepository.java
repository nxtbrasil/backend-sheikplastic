package com.sheikplastic.repository;

import com.sheikplastic.model.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportadoraRepository
        extends JpaRepository<Transportadora, Integer> {

    List<Transportadora> findByAtivoTrue();
}
