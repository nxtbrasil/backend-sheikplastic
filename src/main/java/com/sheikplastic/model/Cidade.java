package com.sheikplastic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCidade;

    @Column(length = 60, nullable = false)
    private String nomeCidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEstado", nullable = false)
    private Estado estado;

    // Getters e Setters
    public Long getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Long idCidade) {
        this.idCidade = idCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
