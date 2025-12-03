package com.sheikplastic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PessoaContato")
public class PessoaContato {

    @EmbeddedId
    private PessoaContatoId id;

    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "idPessoa")
    @JsonIgnore
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "idTipoContato")
    private TipoContato tipoContato;

    @Column(name = "contato")
    private String contato;

    @Column(name = "observacao")
    private String observacao;

    // GETTERS E SETTERS

    public Integer getSeqContato() { return id.getSeqContato(); }
    public void setSeqContato(Integer seqContato) { 
        id.setSeqContato(seqContato); 
    }
}