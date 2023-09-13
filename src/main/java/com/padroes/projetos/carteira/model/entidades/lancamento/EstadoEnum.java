package com.padroes.projetos.carteira.model.entidades.lancamento;

public enum EstadoEnum {
    PROCESSADO("PROCESSADO"),
    AGUARDANDO("AGUARDANDO"),
    CANCELADO("CANCELADO");

    private final String valor;

    EstadoEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
