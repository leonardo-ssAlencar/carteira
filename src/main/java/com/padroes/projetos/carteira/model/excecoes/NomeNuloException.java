package com.padroes.projetos.carteira.model.excecoes;

public class NomeNuloException extends RuntimeException {

    public NomeNuloException(String msg) {
        super(msg);
    }
}
