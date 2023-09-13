package com.padroes.projetos.carteira.model.excecoes;

public class EntidadeNaoCadastradaException extends RuntimeException {

    private Long grupoId;

    public EntidadeNaoCadastradaException(String msg) {
        super(msg);
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

}
