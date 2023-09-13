package com.padroes.projetos.carteira.model.entidades.lancamento;

import java.math.BigDecimal;
import java.util.List;

import com.padroes.projetos.carteira.model.entidades.Item;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.enums.OperacoesEnum;

public class LancamentoComItemsFactory implements LancamentoFactory {

    private Lancamento lancamento;

    public LancamentoComItemsFactory(List<Item> items) {
        this.lancamento = new LancamentoComItems(items);

    }

    @Override
    public Lancamento criarLancamento(Usuario user, BigDecimal valor, String msg, OperacoesEnum operacao) {
        lancamento.usuario = user;
        lancamento.mensagem = msg;
        lancamento.valor = valor;
        lancamento.operacao = operacao;

        return this.lancamento;
    }

}
