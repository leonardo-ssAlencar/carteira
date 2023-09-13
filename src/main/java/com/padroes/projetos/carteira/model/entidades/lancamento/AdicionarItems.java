package com.padroes.projetos.carteira.model.entidades.lancamento;

import java.math.BigDecimal;
import java.util.List;

import com.padroes.projetos.carteira.model.entidades.Item;
import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;
import com.padroes.projetos.carteira.model.entidades.caixinha.CaixinhaComItens;
import com.padroes.projetos.carteira.model.excecoes.OperacaoNaoPermitidaException;

public class AdicionarItems implements Operacao {

    private List<Item> items;

    public AdicionarItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public BigDecimal executarOperacao(Caixinha caixinha) {

        // TODO Impl
        if (!(caixinha instanceof CaixinhaComItens)) {
            throw new OperacaoNaoPermitidaException("A caixinha tem que aceitar itens");

        }

        items.size();
        return new BigDecimal(0);
    }

}
