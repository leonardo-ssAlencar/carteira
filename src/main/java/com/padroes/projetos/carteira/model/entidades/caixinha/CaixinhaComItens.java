package com.padroes.projetos.carteira.model.entidades.caixinha;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.padroes.projetos.carteira.model.entidades.Item;
import com.padroes.projetos.carteira.model.enums.estrategias.EstrategiaEstornoEnum;
import com.padroes.projetos.carteira.model.enums.estrategias.EstrategiaNotificacaoEnum;
import com.padroes.projetos.carteira.model.enums.estrategias.LancamentoEstrategyEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("CI")
public class CaixinhaComItens extends Caixinha {

    @OneToMany(mappedBy = "caixinha", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> itens = new ArrayList<>();

    public CaixinhaComItens(List<Item> itens, EstrategiaNotificacaoEnum notificador, EstrategiaEstornoEnum estorno,
            BigDecimal valorTotal, BigDecimal meta, LocalDate fechamento, boolean mensal,
            LancamentoEstrategyEnum lancamentoEstrategy) {
        super(notificador, estorno, valorTotal, meta, fechamento, mensal, lancamentoEstrategy);
        setItens(itens);
    }

    public CaixinhaComItens() {
    }

    public void setItens(List<Item> itens) {
        itens.forEach(x -> x.setCaixinha(this));
        this.itens.addAll(itens);

    }

    public List<Item> getItens() {
        return this.itens;
    }

    public void adicionarItens(List<Item> itens) {

    }

}
