
package br.com.movelearn.model;

import java.time.LocalDateTime;

public class Usuario {

    private Long usuarioId;
    private String nome;
    private String email;
    private String senhaHash;
    private LocalDateTime dtCriacao;

    public Usuario() {
    }

    public Usuario(Long usuarioId, String nome, String email,
                   String senhaHash, LocalDateTime dtCriacao) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.dtCriacao = dtCriacao;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public LocalDateTime getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDateTime dtCriacao) {
        this.dtCriacao = dtCriacao;
    }
}
