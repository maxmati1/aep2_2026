package com.cidadaoativo.domain;

public enum TipoUsuario {
    CIDADAO("Cidadao"),
    ATENDENTE("Atendente"),
    GESTOR("Gestor");

    private final String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
