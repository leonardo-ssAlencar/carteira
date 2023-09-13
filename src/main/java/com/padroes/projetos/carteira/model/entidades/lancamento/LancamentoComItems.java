package com.padroes.projetos.carteira.model.entidades.lancamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.padroes.projetos.carteira.model.entidades.Item;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.enums.OperacoesEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
@DiscriminatorValue("LANC_ITEM")
public class LancamentoComItems extends Lancamento {

    @OneToMany(mappedBy = "lancamentoItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<ItensLancamento> itens;
    @Transient
    private List<Item> nItems;

    public LancamentoComItems(List<Item> items) {
        this.nItems = items;
        criarDescItens(items);
    }

    public LancamentoComItems(Usuario usuario, String mensagem, BigDecimal valor, OperacoesEnum operacao,
            LocalDateTime dataHoraLancamento, List<Item> itens) {
        super(usuario, mensagem, operacao, dataHoraLancamento);
        this.nItems = itens;
        criarDescItens(itens);
    }

    @Override
    public Operacao getOperacao() {
        // if (operacao == OperacoesEnum.ADD_ITEMS) {
        // return new AdicionarItems(nItems);
        // }
        return super.getOperacao();
    }

    public List<ItensLancamento> getItens() {
        return itens;
    }

    private void criarDescItens(List<Item> itens) {
        this.itens = itens.stream().map(x -> ItensLancamento.criar(x)).toList();

    }

}
