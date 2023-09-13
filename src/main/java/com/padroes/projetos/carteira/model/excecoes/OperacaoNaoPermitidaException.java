package com.padroes.projetos.carteira.model.excecoes;

public class OperacaoNaoPermitidaException extends RuntimeException {

    public OperacaoNaoPermitidaException(String msg) {
        super(msg);
    }

}
