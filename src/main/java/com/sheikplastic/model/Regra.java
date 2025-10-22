package com.sheikplastic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Regra")
public class Regra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegra;

    private String chaveRegra;

    public Long getIdRegra() { return idRegra; }
    public void setIdRegra(Long idRegra) { this.idRegra = idRegra; }

    public String getChaveRegra() { return chaveRegra; }
    public void setChaveRegra(String chaveRegra) { this.chaveRegra = chaveRegra; }
}