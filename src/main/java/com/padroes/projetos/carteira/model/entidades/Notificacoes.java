package com.padroes.projetos.carteira.model.entidades;

import java.time.LocalDateTime;

import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Notificacoes implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private LocalDateTime dataHoraEnvio;
    private boolean lida;
    @ManyToOne
    private Usuario usuario;

    public Notificacoes() {
    }

    public Notificacoes(String msg) {
        this.mensagem = msg;
        this.dataHoraEnvio = LocalDateTime.now();
        this.lida = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        this.marcarLida();
        return mensagem;
    }

    public LocalDateTime getDataHoraEnvio() {
        return dataHoraEnvio;
    }

    public LocalDateTime getEnviada() {
        return dataHoraEnvio;
    }

    public boolean isLida() {
        return this.lida;
    }

    public void marcarLida() {
        this.lida = true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Notificacoes clone() {

        try {
            return (Notificacoes) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public String toString() {
        return "Notificacoes [id=" + id + ", mensagem=" + mensagem + ", dataHoraEnvio=" + dataHoraEnvio + ", lida="
                + lida + ", usuario=" + usuario.getNome() + "]";
    }

}
