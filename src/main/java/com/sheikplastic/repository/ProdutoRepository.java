package com.sheikplastic.repository;

import com.sheikplastic.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeProdutoContainingIgnoreCase(String nomeProduto);
}
