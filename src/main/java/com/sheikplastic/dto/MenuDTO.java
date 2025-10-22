package com.sheikplastic.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuDTO {
    private Long id;
    private String descricao;
    private String endereco;
    private List<MenuDTO> subMenus = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public List<MenuDTO> getSubMenus() { return subMenus; }
    public void setSubMenus(List<MenuDTO> subMenus) { this.subMenus = subMenus; }
}
