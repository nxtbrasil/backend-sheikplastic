package com.sheikplastic.repository;

import com.sheikplastic.model.PessoaContato;
import com.sheikplastic.model.PessoaContatoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaContatoRepository 
    extends JpaRepository<PessoaContato, PessoaContatoId> {

    List<PessoaContato> findByPessoa_IdOrderById_SeqContato(Long idPessoa);
}