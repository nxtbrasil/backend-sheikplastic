package com.sheikplastic.service;

import com.sheikplastic.config.JwtUtil;
import com.sheikplastic.dto.LoginResponse;
import com.sheikplastic.dto.TrocaSenhaRequest;
import com.sheikplastic.model.Funcionario;
import com.sheikplastic.repository.FuncionarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;

@Service
public class AuthService {

    private final FuncionarioRepository funcionarioRepository;
    private final JwtUtil jwtUtil;
    private final GrupoService grupoService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(FuncionarioRepository funcionarioRepository, JwtUtil jwtUtil, GrupoService grupoService) {
        this.funcionarioRepository = funcionarioRepository;
        this.jwtUtil = jwtUtil;
        this.grupoService = grupoService;
    }
    
public LoginResponse login(String email, String senha) {
    Funcionario f = funcionarioRepository.findByEmailFuncionarioAndAtivoTrue(email)
            .orElseThrow(() -> new IllegalArgumentException("E-mail não encontrado"));

    // Converter VARBINARY para String (UTF-16LE é o padrão do ASP antigo)
    String senhaBanco = new String(f.getSenhaFuncionario(), java.nio.charset.StandardCharsets.UTF_16LE).trim();

    boolean senhaValida = false;

    // Verifica se é hash BCrypt
    if (senhaBanco.startsWith("$2a$") || senhaBanco.startsWith("$2b$")) {
        senhaValida = passwordEncoder.matches(senha, senhaBanco);
    } else {
        // Senha legada em texto simples
        senhaValida = senhaBanco.equals(senha);
    }

    if (!senhaValida) {
        throw new BadCredentialsException("Senha incorreta");
    }

    Set<Integer> grupos = grupoService.getGruposDoFuncionario(f.getIdFuncionario());
    String token = jwtUtil.generateToken(f.getEmailFuncionario());

    return new LoginResponse(f.getIdFuncionario(), f.getNomeFuncionario(), token, grupos);
}

public void trocarSenha(TrocaSenhaRequest req) {
         Funcionario funcionario = funcionarioRepository.findByEmailFuncionario(req.getEmailFuncionario())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));

        // Verifica senha atual
        byte[] senhaAtualBytes = req.getSenhaAtual().getBytes(StandardCharsets.UTF_16LE);
        if (!Arrays.equals(senhaAtualBytes, funcionario.getSenhaFuncionario())) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        // Converte nova senha para bytes
        byte[] novaSenhaBytes = req.getNovaSenha().getBytes(StandardCharsets.UTF_16LE);
        funcionario.setSenhaFuncionario(novaSenhaBytes);

        funcionarioRepository.save(funcionario);
    }


    public Funcionario register(String nome, String email, String senha) {
        if (funcionarioRepository.findByEmailFuncionarioAndAtivoTrue(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        Funcionario f = new Funcionario();
        f.setNomeFuncionario(nome);
        f.setEmailFuncionario(email);
        // f.setSenhaFuncionario(passwordEncoder.encode(senha));
        f.setAtivo(true);
        return funcionarioRepository.save(f);
    }

}
