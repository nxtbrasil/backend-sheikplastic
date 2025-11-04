package com.sheikplastic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sheikplastic.dto.FuncionarioGrupoDTO;
import com.sheikplastic.model.Funcionario;
import com.sheikplastic.model.GrupoUsuario;
import com.sheikplastic.repository.FuncionarioRepository;
import com.sheikplastic.repository.GrupoUsuarioFuncionarioRepository;
import com.sheikplastic.repository.GrupoUsuarioRepository;

@Service
@Transactional

public class GrupoUsuarioService {

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository; 

    @Autowired
    private GrupoUsuarioFuncionarioRepository grupoUsuarioFuncionarioRepository;

    public List<GrupoUsuario> listarTodos() {
        return grupoUsuarioRepository.findAll();
    }

    public GrupoUsuario buscarPorId(Integer id) {
        return grupoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo de usuário não encontrado"));
    }

    public GrupoUsuario salvar(GrupoUsuario grupoUsuario) {
        return grupoUsuarioRepository.save(grupoUsuario);
    }

    public GrupoUsuario atualizar(Integer id, GrupoUsuario grupoUsuarioAtualizado) {
        GrupoUsuario grupo = buscarPorId(id);
        grupo.setNomeGrupoUsuario(grupoUsuarioAtualizado.getNomeGrupoUsuario());
        return grupoUsuarioRepository.save(grupo);
    }

    public void deletar(Integer id) {
        grupoUsuarioRepository.deleteById(id);
    }

    public List<FuncionarioGrupoDTO> listarFuncionariosPorGrupo(Integer idGrupoUsuario) {
    List<Funcionario> funcionarios = funcionarioRepository.findAll();
    List<FuncionarioGrupoDTO> resultado = new ArrayList<>();

    for (Funcionario f : funcionarios) {
        // Conta quantos vínculos esse funcionário tem com o grupo especificado
        long qtdVinculos = grupoUsuarioFuncionarioRepository
                .countById_IdFuncionarioAndId_IdGrupoUsuario(f.getIdFuncionario(), idGrupoUsuario);

        FuncionarioGrupoDTO dto = new FuncionarioGrupoDTO();
        dto.setIdFuncionario(f.getIdFuncionario());
        dto.setNomeFuncionario(f.getNomeFuncionario());
        dto.setEmailFuncionario(f.getEmailFuncionario());
        dto.setQtdVinculos(qtdVinculos); // adiciona o campo da query
        dto.setVinculado(qtdVinculos > 0);

        resultado.add(dto);
    }

    return resultado;
}
}
