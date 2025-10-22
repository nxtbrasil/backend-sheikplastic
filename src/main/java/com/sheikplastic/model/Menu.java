package com.sheikplastic.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMenu;

    private String descricaoMenu;
    private String enderecoPagina;
    private Integer ordemMenu;

    @ManyToOne
    @JoinColumn(name = "idMenuSuperior")
    private Menu menuSuperior;

    @ManyToOne
    @JoinColumn(name = "idRegra")
    private Regra regra;

    @OneToMany(mappedBy = "menuSuperior", fetch = FetchType.LAZY)
    @OrderBy("ordemMenu ASC")
    private List<Menu> subMenus = new ArrayList<>();

    // Campo alterado de "ativo" -> "visivel"
    @Column(name = "visivel")
    private Boolean visivel;

    // Getters e Setters
    public Long getIdMenu() { return idMenu; }
    public void setIdMenu(Long idMenu) { this.idMenu = idMenu; }

    public String getDescricaoMenu() { return descricaoMenu; }
    public void setDescricaoMenu(String descricaoMenu) { this.descricaoMenu = descricaoMenu; }

    public String getEnderecoPagina() { return enderecoPagina; }
    public void setEnderecoPagina(String enderecoPagina) { this.enderecoPagina = enderecoPagina; }

    public Integer getOrdemMenu() { return ordemMenu; }
    public void setOrdemMenu(Integer ordemMenu) { this.ordemMenu = ordemMenu; }

    public Menu getMenuSuperior() { return menuSuperior; }
    public void setMenuSuperior(Menu menuSuperior) { this.menuSuperior = menuSuperior; }

    public Regra getRegra() { return regra; }
    public void setRegra(Regra regra) { this.regra = regra; }

    public List<Menu> getSubMenus() { return subMenus; }
    public void setSubMenus(List<Menu> subMenus) { this.subMenus = subMenus; }

    public Boolean getVisivel() { return visivel; }
    public void setVisivel(Boolean visivel) { this.visivel = visivel; }
}
