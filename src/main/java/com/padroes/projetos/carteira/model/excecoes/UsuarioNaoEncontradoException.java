package com.padroes.projetos.carteira.model.excecoes;

public class UsuarioNaoEncontradoException extends RuntimeException {

    private Long grupoId;

    public UsuarioNaoEncontradoException(String msg, Long grupoId) {

        super(msg);
        this.grupoId = grupoId;

    }

    public Long getGrupoId() {
        return grupoId;
    }

}
