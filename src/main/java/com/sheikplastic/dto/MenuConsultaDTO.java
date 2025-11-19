package com.sheikplastic.dto;

import lombok.Data;

@Data
public class MenuConsultaDTO {
    private Long idMenu;
    private String descricaoMenu;
    private String enderecoPagina;
    private Integer ordemMenu;
    private Long qtdSubMenus;
}
