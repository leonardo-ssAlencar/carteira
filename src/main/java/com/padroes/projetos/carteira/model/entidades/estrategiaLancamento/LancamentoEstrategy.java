package com.padroes.projetos.carteira.model.entidades.estrategiaLancamento;

import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;
import com.padroes.projetos.carteira.model.entidades.commands.LancamentoCommand;
import com.padroes.projetos.carteira.model.entidades.lancamento.Lancamento;

public interface LancamentoEstrategy {

    public abstract Lancamento executar(Caixinha caixinha, LancamentoCommand command);

}
