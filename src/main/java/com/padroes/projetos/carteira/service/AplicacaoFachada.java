package com.padroes.projetos.carteira.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;
import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;
import com.padroes.projetos.carteira.model.entidades.grupo.Grupo;
import com.padroes.projetos.carteira.model.entidades.grupo.GrupoComponent;
import com.padroes.projetos.carteira.model.entidades.grupo.GrupoFachada;
import com.padroes.projetos.carteira.model.entidades.grupo.Participante;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.entidades.lancamento.Lancamento;
import com.padroes.projetos.carteira.model.excecoes.EntidadeNaoCadastradaException;
import com.padroes.projetos.carteira.model.excecoes.UsuarioNaoExisteException;
import com.padroes.projetos.carteira.repository.RepositorioCaixinha;
import com.padroes.projetos.carteira.repository.RepositorioGrupo;
import com.padroes.projetos.carteira.repository.RepositorioLancamento;
import com.padroes.projetos.carteira.repository.RepositorioNotificacoes;
import com.padroes.projetos.carteira.repository.RepositorioParticipante;
import com.padroes.projetos.carteira.repository.RepositorioUsuario;

@Service
public class AplicacaoFachada {

    @Autowired
    RepositorioUsuario usuarioRepo;
    @Autowired
    RepositorioGrupo grupoRepo;
    @Autowired
    RepositorioCaixinha caixinhaRepo;
    @Autowired
    RepositorioParticipante participanteRepo;
    @Autowired
    RepositorioLancamento lancamentoRepo;
    @Autowired
    RepositorioNotificacoes notificacaoRepo;

    @Autowired
    GrupoFachada fachada;

    /**
     * Cadastra um Usuario
     * 
     * @param usuario
     * @return
     */

    public Usuario cadastrarUsuario(Usuario usuario) {

        usuarioRepo.save(usuario);

        Grupo grupo = fachada.criarGrupoUsuario(usuario);

        grupoRepo.save(grupo);

        return usuarioRepo.save(usuario);
    }

    /**
     * Cadastra um grupo
     * 
     * @param grupo
     * @return
     */
    public Grupo cadastrarGrupo(Grupo grupo) {

        caixinhaRepo.save(grupo.getCaixinha());

        grupo = grupoRepo.save(grupo);

        return grupo;

    }

    /**
     * Testa se o usuario tem conta. Use pra fazer login.
     * 
     * @param email
     * @param senha
     * @return a conta do usuario
     */

    public Optional<Usuario> validarUsuario(String email, String senha) {
        Optional<Usuario> userOpt = usuarioRepo.findOneByEmail(email);

        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();
            if (user.getSenha().equals(senha)) {
                return userOpt;
            }
        }

        return Optional.empty();

    }

    public Participante buscarUsuario(String email) throws EntidadeNaoCadastradaException {

        Optional<Usuario> user = usuarioRepo.findOneByEmail(email);

        if (user.isEmpty()) {
            throw new EntidadeNaoCadastradaException("Não existe usuario com esse email");
        }

        return new Participante(user.get());

    }

    public Usuario buscarUsuario(Long email) throws EntidadeNaoCadastradaException {

        Optional<Usuario> user = usuarioRepo.findById(email);

        if (user.isEmpty()) {
            throw new EntidadeNaoCadastradaException("Não existe usuario com esse email");
        }

        return user.get();

    }

    public Grupo buscarGrupo(Long id) throws EntidadeNaoCadastradaException {

        Optional<Grupo> grupo = grupoRepo.findById(id);
        if (grupo.isEmpty()) {
            throw new EntidadeNaoCadastradaException("O grupo nao existe");

        }

        return grupo.get();

    }

    /**
     * Cadastra ou atualiza um participante no grupo
     * 
     * @param participante
     * @param grupo
     * @return
     */
    public Participante cadastrarParticipante(GrupoComponent usuario, Grupo grupo) {

        Participante participante = new Participante(usuario);

        Grupo root = (Grupo) usuario.getParente();

        participante.setGrupo(grupo);
        Participante gParticipante = new Participante(grupo);
        gParticipante.setGrupo(root);

        participanteRepo.save(gParticipante);
        return participanteRepo.save(participante);

    }

    public void deletarParticipante(Participante participante) {

        participanteRepo.delete(participante);

    }

    /**
     * Salva ou atualiza um lancamento no banco
     * 
     * @param lancamento
     * @return Lancamento com o id
     */
    public Lancamento salvarLancamento(Lancamento lancamento) {

        return lancamentoRepo.save(lancamento);

    }

    /**
     * Salva as notificacoes no banco
     * 
     * @param notificacoes lista das notificacoes
     */
    public void salvarNotificacoes(List<Notificacoes> notificacoes) {

        notificacaoRepo.saveAll(notificacoes);

    }

    public void salvarNotificacoes(Notificacoes notificacoes) {

        notificacaoRepo.save(notificacoes);

    }

    public List<BigDecimal> valorLancamentos(GrupoComponent grupoComp) {

        if (grupoComp instanceof Usuario) {
            return lancamentoRepo.valoresLancamentos((Usuario) grupoComp);
        }
        Grupo grupo = (Grupo) grupoComp;
        return lancamentoRepo.valoresLancamentos(grupo.getCaixinha());

    }

    public List<Participante> participantes(Grupo grupoUser) {
        return participanteRepo.participantes(grupoUser);
    }

    public Participante participante(GrupoComponent user, Grupo grupo) {

        Optional<Participante> participante;
        if (user instanceof Grupo) {
            participante = participanteRepo.participantesGrupo(grupo, (Grupo) user);

        } else {
            participante = participanteRepo.participantesGrupo(grupo, (Usuario) user);
        }

        if (participante.isEmpty()) {
            throw new UsuarioNaoExisteException("O usuario não está nesse grupo!!!");

        }

        return participante.get();

    }

    public List<Lancamento> lancamentos(GrupoComponent grupoComp) {

        if (grupoComp instanceof Usuario) {
            return lancamentoRepo.lancamentos((Usuario) grupoComp);
        }
        Grupo grupo = (Grupo) grupoComp;
        return lancamentoRepo.lancamentos(grupo.getCaixinha());

    }

    public Caixinha caixinha(Grupo grupo) {

        return caixinhaRepo.findById(grupo.getCaixinha().getId()).get();
    }

    public Participante buscarParticipante(Long id) {
        Optional<Participante> participante = participanteRepo.findById(id);

        if (participante.isEmpty()) {
            throw new EntidadeNaoCadastradaException("O participante nao existe");

        }

        return participante.get();

    }

    public Participante participante(GrupoComponent component) {

        Optional<Participante> p = participanteRepo.participante(component);

        if (p.isEmpty()) {
            throw new EntidadeNaoCadastradaException("O participante nao existe");
        }

        return p.get();

    }

    public void salvarParticipante(Participante participante) {

        participanteRepo.save(participante);

    }

    public List<Notificacoes> notificacoes(Usuario user) {

        return notificacaoRepo.notificacaoUsuario(user);

    }

    public void deletarParticipantes(List<Participante> participantes) {
        participanteRepo.deleteAll(participantes);
    }

    public void deletarLancamentos(List<Lancamento> lancamentos) {
        lancamentoRepo.deleteAll(lancamentos);

    }

    public void deletarGrupo(Grupo grupo) {
        grupoRepo.delete(grupo);
    }

}
