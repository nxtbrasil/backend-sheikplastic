package com.sheikplastic.service;

import com.sheikplastic.dto.MenuDTO;
import com.sheikplastic.model.Menu;
import com.sheikplastic.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RegraService regraService;

    /**
     * Retorna o menu completo (hierárquico) do funcionário,
     * filtrando conforme suas regras de acesso.
     */
    public List<MenuDTO> getMenuFuncionario(Long idFuncionario) {
        Set<String> regrasUsuario = regraService.getRegrasFuncionario(idFuncionario);

        // Busca apenas menus raiz visíveis
        List<Menu> menusRaiz = menuRepository.findByMenuSuperiorIsNullAndVisivelTrueOrderByOrdemMenu();

        return menusRaiz.stream()
                .filter(menu -> temAcesso(menu, regrasUsuario))
                .map(menu -> toDTO(menu, regrasUsuario))
                .collect(Collectors.toList());
    }

    /**
     * Verifica se o funcionário tem acesso ao menu atual.
     */
    private boolean temAcesso(Menu menu, Set<String> regrasUsuario) {
        if (menu.getRegra() != null && menu.getRegra().getChaveRegra() != null) {
            return regrasUsuario.contains(menu.getRegra().getChaveRegra());
        }

        // Se o menu não tem regra, verifica se algum submenu é acessível
        return menu.getSubMenus() != null && menu.getSubMenus().stream()
                .filter(Menu::getVisivel)
                .anyMatch(sub -> temAcesso(sub, regrasUsuario));
    }

    /**
     * Converte o menu em DTO e aplica recursivamente os submenus acessíveis.
     */
    private MenuDTO toDTO(Menu menu, Set<String> regrasUsuario) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getIdMenu());
        dto.setDescricao(menu.getDescricaoMenu());
        dto.setEndereco(menu.getEnderecoPagina());

        List<MenuDTO> subMenus = menu.getSubMenus().stream()
                .filter(Menu::getVisivel)
                .filter(sub -> temAcesso(sub, regrasUsuario))
                .map(sub -> toDTO(sub, regrasUsuario))
                .collect(Collectors.toList());

        dto.setSubMenus(subMenus);
        return dto;
    }
}
