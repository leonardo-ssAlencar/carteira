package com.padroes.projetos.carteira.model.entidades.estrategiaLancamento;

import java.time.LocalDateTime;

import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;
import com.padroes.projetos.carteira.model.entidades.commands.LancamentoCommand;
import com.padroes.projetos.carteira.model.entidades.lancamento.EstadoEnum;
import com.padroes.projetos.carteira.model.entidades.lancamento.Lancamento;

public class LancamentoSimples implements LancamentoEstrategy {

    @Override
    public Lancamento executar(Caixinha caixinha, LancamentoCommand command) {
        Lancamento lancamento = command.getLancamento();

        lancamento.setDataHoraLancamento(LocalDateTime.now());

        lancamento.setValor(lancamento.getOperacao().executarOperacao(caixinha));

        lancamento.setEstado(EstadoEnum.PROCESSADO);

        return lancamento;

    }

}
