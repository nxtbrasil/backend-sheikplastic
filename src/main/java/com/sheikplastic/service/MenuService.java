package com.sheikplastic.service;

import com.sheikplastic.dto.MenuDTO;
import com.sheikplastic.model.Menu;
import com.sheikplastic.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    /**
     * Retorna TODO o menu hierárquico visível
     * (sem regras, igual ao ASP clássico).
     */
    public List<MenuDTO> getMenu() {

        // Pega apenas os menus raiz (pai null)
        List<Menu> menusRaiz =
                menuRepository.findByMenuSuperiorIsNullAndVisivelTrueOrderByOrdemMenu();

        return menusRaiz.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Monta o DTO e seus submenus (recursivo)
     */
    private MenuDTO toDTO(Menu menu) {
        MenuDTO dto = new MenuDTO();

        dto.setId(menu.getIdMenu());
        dto.setDescricao(menu.getDescricaoMenu());
        dto.setEndereco(menu.getEnderecoPagina());

        List<MenuDTO> subMenus =
                menu.getSubMenus().stream()
                        .filter(Menu::getVisivel)
                        .sorted((a, b) -> a.getOrdemMenu().compareTo(b.getOrdemMenu()))
                        .map(this::toDTO)
                        .collect(Collectors.toList());

        dto.setSubMenus(subMenus);

        return dto;
    }


    /**
     * Retorna o menu hierárquico visível para um funcionário específico
     * (com base nas suas permissões).
     */
    public List<MenuDTO> getMenuFuncionario(Long idFuncionario) {
        // Pega apenas os menus raiz (pai null)
        List<Menu> menusRaiz =
                menuRepository.findByMenuSuperiorIsNullAndVisivelTrueOrderByOrdemMenu();

        return menusRaiz.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}