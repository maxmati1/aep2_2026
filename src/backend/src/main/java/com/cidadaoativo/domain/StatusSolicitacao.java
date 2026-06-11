package com.cidadaoativo.domain;

public enum StatusSolicitacao {
    ABERTO("Aberto"),
    TRIAGEM("Triagem"),
    EM_EXECUCAO("Em Execucao"),
    RESOLVIDO("Resolvido"),
    ENCERRADO("Encerrado");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
