package com.sheikplastic.controller;

import com.sheikplastic.dto.LoginRequest;
import com.sheikplastic.dto.LoginResponse;
import com.sheikplastic.dto.TrocaSenhaRequest;
import com.sheikplastic.model.Funcionario;
import com.sheikplastic.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável por autenticação, registro e troca de senha.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 🔐 Login do funcionário.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request) {
        try {
            LoginResponse res = authService.login(request.getEmail(), request.getSenha());
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            // Exceção para erro de credenciais inválidas (ex: email não encontrado)
            return ResponseEntity.status(401).body(new ErrorResponse("email", e.getMessage()));
        } catch (Exception e) {
            // Exceção genérica — erro inesperado no processo de autenticação
            return ResponseEntity.status(401).body(new ErrorResponse("senha", e.getMessage()));
        }
    }

    /**
     * 🧾 Registro de novo funcionário.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            Funcionario novoFuncionario = authService.register(req.nome(), req.email(), req.senha());
            return ResponseEntity.ok(novoFuncionario);
        } catch (IllegalArgumentException e) {
            // Mensagem amigável para erros de validação (ex: email já cadastrado)
            return ResponseEntity.badRequest().body(new ErrorResponse("cadastro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("erro", "Falha ao registrar funcionário."));
        }
    }

    /**
     * 🔄 Troca de senha.
     */
    @PutMapping("/trocar-senha")
    public ResponseEntity<?> trocarSenha(@RequestBody TrocaSenhaRequest req) {
        try {
            authService.trocarSenha(req);
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("erro", "Erro inesperado ao trocar senha."));
        }
    }

    // 🧱 DTOs internos (podem ser movidos para um pacote dto se preferir)
    public static record RegisterRequest(String nome, String email, String senha) {}
    public static record ErrorResponse(String campo, String mensagem) {}
}
