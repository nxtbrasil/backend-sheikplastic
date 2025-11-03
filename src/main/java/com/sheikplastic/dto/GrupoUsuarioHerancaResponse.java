package com.sheikplastic.dto;

import java.util.List;

public class GrupoUsuarioHerancaResponse {

    private GrupoPaiDTO grupoPai;
    private String filtroAplicado;
    private List<GrupoFilhoDTO> grupos;

    public GrupoPaiDTO getGrupoPai() {
        return grupoPai;
    }

    public void setGrupoPai(GrupoPaiDTO grupoPai) {
        this.grupoPai = grupoPai;
    }

    public String getFiltroAplicado() {
        return filtroAplicado;
    }

    public void setFiltroAplicado(String filtroAplicado) {
        this.filtroAplicado = filtroAplicado;
    }

    public List<GrupoFilhoDTO> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoFilhoDTO> grupos) {
        this.grupos = grupos;
    }

    public static class GrupoPaiDTO {
        private Integer id;
        private String nome;

        public GrupoPaiDTO(Integer id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Integer getId() { return id; }
        public String getNome() { return nome; }
    }

    public static class GrupoFilhoDTO {
        private Integer idGrupoUsuario;
        private String nomeGrupoUsuario;
        private boolean vinculado;

        public GrupoFilhoDTO(Integer idGrupoUsuario, String nomeGrupoUsuario, boolean vinculado) {
            this.idGrupoUsuario = idGrupoUsuario;
            this.nomeGrupoUsuario = nomeGrupoUsuario;
            this.vinculado = vinculado;
        }

        public Integer getIdGrupoUsuario() { return idGrupoUsuario; }
        public String getNomeGrupoUsuario() { return nomeGrupoUsuario; }
        public boolean isVinculado() { return vinculado; }
    }
}
