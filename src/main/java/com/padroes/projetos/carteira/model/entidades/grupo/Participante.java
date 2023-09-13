package com.padroes.projetos.carteira.model.entidades.grupo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * Classe interna para controle do grupo.
 * Tem alguns metodos uteis, e o controle do administrador
 */

@Entity
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private GrupoComponent componente;
    private boolean eAdmin;
    @ManyToOne
    private Grupo grupo;

    public Participante() {
    }

    public Participante(GrupoComponent participante) {
        if (participante == null) {
            // Lançar uma excessão
        }
        this.componente = participante;
        this.eAdmin = false;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GrupoComponent getParticipante() {
        return componente;
    }

    public boolean eAdmin() {
        return this.eAdmin;
    }

    public void tornarAdmin(boolean eAdmin) {
        this.eAdmin = eAdmin;

    }

    public boolean eUsuario() {
        return this.componente instanceof Usuario;
    }

    public boolean eGrupo() {
        return this.componente instanceof Grupo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((componente == null) ? 0 : componente.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Participante other = (Participante) obj;
        if (componente == null) {
            if (other.componente != null)
                return false;
        } else if (!componente.equals(other.componente))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Participante [id=" + id + ", participante=" + componente.getNome() + ", eAdmin=" + eAdmin + "]";
    }

}
