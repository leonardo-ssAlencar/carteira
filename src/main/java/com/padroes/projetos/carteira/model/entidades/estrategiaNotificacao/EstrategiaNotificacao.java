package com.padroes.projetos.carteira.model.entidades.estrategiaNotificacao;

import java.util.List;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;
import com.padroes.projetos.carteira.model.entidades.grupo.Participante;

public interface EstrategiaNotificacao {

    public abstract List<Notificacoes> notificar(String mensagem, List<Participante> participantes);

}
