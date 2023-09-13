package com.padroes.projetos.carteira.model.entidades.commands;

import java.math.BigDecimal;

import com.padroes.projetos.carteira.model.entidades.grupo.Grupo;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.entidades.lancamento.Lancamento;
import com.padroes.projetos.carteira.model.entidades.lancamento.LancamentoFactory;
import com.padroes.projetos.carteira.model.enums.OperacoesEnum;

public class DebitoCommand extends LancamentoCommand {

    private Lancamento lancamento;

    public DebitoCommand(Grupo grupo, BigDecimal valor, Usuario user, String descricao, LancamentoFactory factory) {
        super(grupo);
        this.lancamento = factory.criarLancamento(user, valor, descricao, OperacoesEnum.DESPESA);

    }

    @Override
    public void executar() {
        getGrupo().getCaixinha().executarLancamento(this);
    }

    @Override
    public Lancamento getLancamento() {
        return this.lancamento;
    }

}
