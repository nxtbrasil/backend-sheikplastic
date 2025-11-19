package com.sheikplastic.repository;

import com.sheikplastic.dto.MenuConsultaDTO;
import com.sheikplastic.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Busca apenas menus principais (sem menu superior) e visíveis
    List<Menu> findByMenuSuperiorIsNullAndVisivelTrueOrderByOrdemMenu();

    // Busca submenus visíveis de um menu específico
    List<Menu> findByMenuSuperiorIdMenuAndVisivelTrueOrderByOrdemMenu(Long idMenuSuperior);


}