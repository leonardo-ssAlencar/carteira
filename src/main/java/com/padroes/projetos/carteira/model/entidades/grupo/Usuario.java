package com.padroes.projetos.carteira.model.entidades.grupo;

import java.util.List;
import java.util.Stack;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(name = "email_ix", columnList = "email"))
public final class Usuario extends GrupoComponent {

    private String telefone;
    @Column(unique = true)
    private String email;
    private String senha;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<Notificacoes> notificacoes = new Stack<>();

    public Usuario() {
    }

    public Usuario(String nome, String telefone, String email, String senha) {

        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    @Override
    public void notificar(Notificacoes msg) {
        this.notificacoes.add(msg);
        msg.setUsuario(this);
    }

    public List<Notificacoes> getMensagem() {
        return this.notificacoes;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public GrupoComponent getParente() {
        return parente;
    }

    public void setParente(Grupo parente) {
        this.parente = parente;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", senha="
                + senha + " ]";
    }

}
