package com.padroes.projetos.carteira.model.excecoes;

public class UsuarioNaoExisteException extends RuntimeException {

    public UsuarioNaoExisteException(String msg) {
        super(msg);

    }

}
