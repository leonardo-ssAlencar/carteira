package com.padroes.projetos.carteira.model.entidades.estrategiaEstorno;

import java.math.BigDecimal;

import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;

public class EstornoSimples implements EstrategiaEstorno {

    @Override
    public void calcularExtorno(Caixinha caixinha) {
        int usuarios = caixinha.getGrupo().getUsuarios().size();

        BigDecimal valorTotal = caixinha.getValorTotal();

        valorTotal = valorTotal.divide(new BigDecimal(usuarios));

        String mensagem = "O valor da caixinha " + caixinha.getGrupo().getNome() + " para o mes "
                + caixinha.getFechamento().getMonthValue() + " foi de "
                + valorTotal + " R$";

        caixinha.notificar(mensagem);

    }

}
