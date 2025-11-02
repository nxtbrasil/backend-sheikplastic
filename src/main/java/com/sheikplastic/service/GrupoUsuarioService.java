package com.sheikplastic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheikplastic.model.GrupoUsuario;
import com.sheikplastic.repository.GrupoUsuarioRepository;

@Service
public class GrupoUsuarioService {

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

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
}
