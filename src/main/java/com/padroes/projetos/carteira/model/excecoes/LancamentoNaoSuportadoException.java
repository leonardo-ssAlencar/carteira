package com.padroes.projetos.carteira.model.excecoes;

public class LancamentoNaoSuportadoException extends RuntimeException {

    public LancamentoNaoSuportadoException(String msg) {
        super(msg);
    }

}
