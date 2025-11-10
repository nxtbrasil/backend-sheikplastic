package com.sheikplastic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sheikplastic.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
