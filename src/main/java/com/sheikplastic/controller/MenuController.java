package com.sheikplastic.controller;

import com.sheikplastic.dto.MenuDTO;
import com.sheikplastic.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<List<MenuDTO>> getMenuFuncionario(@PathVariable Long idFuncionario) {
        return ResponseEntity.ok(menuService.getMenuFuncionario(idFuncionario));
    }
}