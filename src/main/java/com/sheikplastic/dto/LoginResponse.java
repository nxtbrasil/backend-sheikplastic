package com.sheikplastic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long idFuncionario;
    private String nomeFuncionario;
    private String token;
    private Set<Integer> grupos;
}
