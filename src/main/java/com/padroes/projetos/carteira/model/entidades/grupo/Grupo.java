package com.padroes.projetos.carteira.model.entidades.grupo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;
import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;
import com.padroes.projetos.carteira.model.excecoes.OperacaoNaoPermitidaException;
import com.padroes.projetos.carteira.model.excecoes.UsuarioNaoExisteException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grupo")
public final class Grupo extends GrupoComponent {

    // O dono do grupo, normalmente quem o criou
    @OneToOne
    private Usuario dono;
    // Lista dos participantes do grupo
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo", cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
    private Set<Participante> participantes = new HashSet<>();
    // A Caixinha do grupo
    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE })
    private Caixinha caixinha;

    public Grupo() {

    }

    public Grupo(String nome, GrupoComponent parente, GrupoComponent dono, Caixinha caixinha) {
        super(nome, parente);
        setDono(dono);

    }

    @Override
    public void notificar(Notificacoes msg) {
        this.participantes.stream().filter(Participante::eUsuario).map(x -> (Usuario) x.getParticipante())
                .forEach(x -> x.notificar(msg.clone()));

    }

    public void notificar(Notificacoes mensagen, Usuario user) {
        Optional<Usuario> usuarioGrupo = this.participantes.stream().filter(Participante::eUsuario)
                .map(x -> (Usuario) x.getParticipante())
                .filter(x -> x.equals(user)).findFirst();

        if (usuarioGrupo.isPresent()) {
            usuarioGrupo.get().notificar(mensagen);
        } else {
            throw new UsuarioNaoExisteException("O usuario " + user.getNome() + " não existe");
        }

    }

    public GrupoComponent getDono() {
        return dono;
    }

    public void setDono(GrupoComponent dono) {
        if (dono instanceof Grupo) {
            Grupo grupoTemp = (Grupo) dono;
            dono = grupoTemp.getDono();
        }
        this.dono = (Usuario) dono;
    }

    public List<Participante> getParticipantes() {
        return this.participantes.stream().toList();
    }

    public boolean setParticipantes(GrupoComponent participante) {

        if (participante instanceof Usuario) {
            verificarRaiz();
        }

        Participante tempP = new Participante(participante);
        tempP.setGrupo(this);

        return this.participantes.add(tempP);

    }

    public Caixinha getCaixinha() {
        return caixinha;
    }

    public void setCaixinha(Caixinha caixinha) {
        this.caixinha = caixinha;
        caixinha.setGrupo(this);
    }

    public void tornarAdmin(Usuario usuario) {
        verificarRaiz();
        Optional<Participante> componente = this.participantes.stream().filter(Participante::eUsuario)
                .filter(x -> x.getParticipante().equals(usuario)).findAny();

        if (componente.isEmpty()) {

            throw new OperacaoNaoPermitidaException("O usuario " + usuario.getNome() + " não está no grupo");
        }

        componente.get().tornarAdmin(true);

    }

    public List<Usuario> getUsuarios() {
        return participantes.stream().filter(Participante::eUsuario).map(x -> (Usuario) x.getParticipante()).toList();

    }

    public List<Usuario> getAdministradores() {

        return participantes.stream().filter(Participante::eUsuario).filter(Participante::eAdmin)
                .map(x -> (Usuario) x.getParticipante()).toList();

    }

    private void verificarRaiz() {
        if (parente == null) {
            throw new OperacaoNaoPermitidaException("A operação não pode ser concluida pois já está na raiz ");
        }
    }

    // @Override
    public GrupoComponent getParente() {
        return this.parente;

    }

    // @Override
    public String getNome() {
        return this.nome;
    }

    // @Override
    public String toString() {
        return "Grupo [id=" + super.getId() + ", nome=" + nome + ", parente=" + parente.getNome() + ", dono="
                + dono.getNome() + "]";
    }

}
