package com.sheikplastic.dto;

public class TrocaSenhaRequest {
    private String emailFuncionario;
    private String senhaAtual;
    private String novaSenha;

    public String getEmailFuncionario() {
        return emailFuncionario;
    }
    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }
    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
