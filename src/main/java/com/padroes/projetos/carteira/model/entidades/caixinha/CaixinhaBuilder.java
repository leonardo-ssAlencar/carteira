package com.padroes.projetos.carteira.model.entidades.caixinha;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import com.padroes.projetos.carteira.model.entidades.Item;
import com.padroes.projetos.carteira.model.enums.estrategias.EstrategiaEstornoEnum;
import com.padroes.projetos.carteira.model.enums.estrategias.EstrategiaNotificacaoEnum;
import com.padroes.projetos.carteira.model.enums.estrategias.LancamentoEstrategyEnum;

public class CaixinhaBuilder {

    protected List<Item> itens;
    protected EstrategiaNotificacaoEnum notificador;
    protected EstrategiaEstornoEnum estorno;
    protected LancamentoEstrategyEnum lancamentoEstrategy;
    protected BigDecimal valorTotal;
    protected BigDecimal meta;
    protected LocalDate fechamento;
    private boolean temItens;
    protected boolean mensal;

    public CaixinhaBuilder() {
        valorTotal = new BigDecimal(0);
        fechamento = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        mensal = true;
        meta = null;
        lancamentoEstrategy = LancamentoEstrategyEnum.LANCAMENTO_SIMPLES;
        notificador = EstrategiaNotificacaoEnum.NOTIFICADOR_SIMPLES;
        estorno = EstrategiaEstornoEnum.ESTORNO_SIMPLES;

    }

    public CaixinhaBuilder notificador(EstrategiaNotificacaoEnum notificador) {
        this.notificador = notificador;

        return this;
    }

    public CaixinhaBuilder estorno(EstrategiaEstornoEnum estrategy) {
        this.estorno = estrategy;

        return this;
    }

    public CaixinhaBuilder items(List<Item> items) {
        this.itens = items;
        this.temItens = true;
        return this;
    }

    public CaixinhaBuilder valorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;

        return this;
    }

    public CaixinhaBuilder meta(BigDecimal meta) {
        this.meta = meta;

        return this;
    }

    public CaixinhaBuilder eMensal(boolean mensal) {
        this.mensal = mensal;

        return this;
    }

    public CaixinhaBuilder fechamento(LocalDate data) {
        this.fechamento = data;
        return this;
    }

    public CaixinhaBuilder lancamento(LancamentoEstrategyEnum estrategy) {
        this.lancamentoEstrategy = estrategy;
        return this;

    }

    public Caixinha build() {

        if (temItens) {
            return new CaixinhaComItens(itens, notificador, estorno, valorTotal, meta, fechamento, mensal,
                    lancamentoEstrategy);

        }
        return new Caixinha(notificador, estorno, valorTotal, meta, fechamento, mensal,
                lancamentoEstrategy);

    }

}
