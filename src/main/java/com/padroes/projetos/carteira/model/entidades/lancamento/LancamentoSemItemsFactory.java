package com.padroes.projetos.carteira.model.entidades.lancamento;

import java.math.BigDecimal;

import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.enums.OperacoesEnum;

public class LancamentoSemItemsFactory implements LancamentoFactory {

    private Lancamento lancamento;

    public LancamentoSemItemsFactory() {
        lancamento = new Lancamento();

    }

    @Override
    public Lancamento criarLancamento(Usuario user, BigDecimal valor, String msg, OperacoesEnum operacao) {
        lancamento.usuario = user;
        lancamento.valor = valor;
        lancamento.mensagem = msg;
        lancamento.operacao = operacao;

        return this.lancamento;
    }

}
