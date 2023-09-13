package com.padroes.projetos.carteira.model.entidades.grupo;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public abstract class GrupoComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    protected String nome;
    @OneToOne
    protected GrupoComponent parente;

    public GrupoComponent(String nome, GrupoComponent parente) {
        this.nome = nome;
        this.parente = parente;
    }

    public GrupoComponent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * O metodo deve retornar o objeto que contem o objeto que chamou o metodo. No
     * caso de um usuario deve retornar o grupo raiz dele, no caso de um subGrupo
     * deve retornar o grupo pai dele
     * 
     * // * @return GrupoComponent parente
     */
    public GrupoComponent getParente() {
        return this.parente;
    }

    /**
     * O metodo deve retornar o nome em que o componente foi cadastrado
     * 
     * // * @return String com o nome do componente
     */
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract void notificar(Notificacoes mensagem);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        GrupoComponent other = (GrupoComponent) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
