package com.sheikplastic.service;

import com.sheikplastic.dto.FuncionarioGrupoDTO;
import com.sheikplastic.model.*;
import com.sheikplastic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GrupoUsuarioFuncionarioService {

    @Autowired
    private GrupoUsuarioFuncionarioRepository grupoUsuarioFuncionarioRepository;

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

public void vincularFuncionarioAoGrupo(Long idFuncionario, Integer idGrupoUsuario)
 {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        GrupoUsuario grupo = grupoUsuarioRepository.findById(idGrupoUsuario)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        GrupoUsuarioFuncionarioId id = new GrupoUsuarioFuncionarioId(idFuncionario, idGrupoUsuario);

        GrupoUsuarioFuncionario vinculo = new GrupoUsuarioFuncionario();
        vinculo.setId(id);
        vinculo.setFuncionario(funcionario);
        vinculo.setGrupoUsuario(grupo);

        grupoUsuarioFuncionarioRepository.save(vinculo);
    }

    public void desvincularFuncionario(Long idFuncionario) {
        grupoUsuarioFuncionarioRepository.deleteById_IdFuncionario(idFuncionario);
    }

    public List<GrupoUsuarioFuncionario> listarPorFuncionario(Long idFuncionario) {
        return grupoUsuarioFuncionarioRepository.findById_IdFuncionario(idFuncionario);
    }

      // ✅ Novo método para atualização
    public void atualizarVinculoFuncionarioAoGrupo(Long idFuncionario, Integer IdGrupoUsuario) {
        // Remove o vínculo anterior (se existir)
        grupoUsuarioFuncionarioRepository.deleteById_IdFuncionario(idFuncionario);

        // Cria o novo vínculo
        vincularFuncionarioAoGrupo(idFuncionario, IdGrupoUsuario);
    }

    // ✅ Novo método
    public List<FuncionarioGrupoDTO> listarTodosComVinculo() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<FuncionarioGrupoDTO> resultado = new ArrayList<>();

        for (Funcionario f : funcionarios) {
        Optional<GrupoUsuarioFuncionario> vinculo = grupoUsuarioFuncionarioRepository
                .findById_IdFuncionario(f.getIdFuncionario())
                .stream()
                .findFirst();

            FuncionarioGrupoDTO dto = new FuncionarioGrupoDTO();
            dto.setIdFuncionario(f.getIdFuncionario());
            dto.setNomeFuncionario(f.getNomeFuncionario());
            dto.setEmailFuncionario(f.getEmailFuncionario());

            if (vinculo.isPresent()) {
                dto.setVinculado(true);
                dto.setNomeGrupo(vinculo.get().getGrupoUsuario().getNomeGrupoUsuario());
            } else {
                dto.setVinculado(false);
                dto.setNomeGrupo(null);
            }

            resultado.add(dto);
        }

        return resultado;
    }
}
