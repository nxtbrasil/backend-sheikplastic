package com.sheikplastic.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
public class PessoaContatoId implements Serializable {

    @Column(name = "idPessoa")
    private Long idPessoa;

    @Column(name = "seqContato")
    private Integer seqContato;

    // equals & hashCode
}
