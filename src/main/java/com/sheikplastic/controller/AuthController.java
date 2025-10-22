package com.sheikplastic.controller;

import com.sheikplastic.dto.LoginRequest;
import com.sheikplastic.dto.LoginResponse;
import com.sheikplastic.model.Funcionario;
import com.sheikplastic.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request) {
        try {
            LoginResponse res = authService.login(request.getEmail(), request.getSenha());
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new ErrorResponse("email", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorResponse("senha", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            Funcionario f = authService.register(req.nome(), req.email(), req.senha());
            return ResponseEntity.ok(f);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public static record RegisterRequest(String nome, String email, String senha) {}
    public static record ErrorResponse(String erro, String mensagem) {}
}
