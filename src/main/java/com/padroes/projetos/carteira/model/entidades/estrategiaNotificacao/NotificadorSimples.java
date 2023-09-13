package com.padroes.projetos.carteira.model.entidades.estrategiaNotificacao;

import java.util.ArrayList;
import java.util.List;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;
import com.padroes.projetos.carteira.model.entidades.grupo.Participante;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;

public class NotificadorSimples implements EstrategiaNotificacao {

    @Override
    public List<Notificacoes> notificar(String mensagem, List<Participante> participantes) {

        List<Notificacoes> notificacoes = new ArrayList<>();

        Notificacoes notificacao = new Notificacoes(mensagem);

        for (Participante p : participantes) {

            if (p.getParticipante() instanceof Usuario) {

                Usuario user = (Usuario) p.getParticipante();
                Notificacoes clone = notificacao.clone();
                user.notificar(clone);

                notificacoes.add(clone);

            }

        }

        return notificacoes;

    }

}
