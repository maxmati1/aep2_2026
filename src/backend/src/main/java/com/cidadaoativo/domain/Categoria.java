package com.cidadaoativo.domain;

public enum Categoria {
    ILUMINACAO("Iluminacao Publica"),
    BURACO_VIA("Buraco na Via"),
    LIMPEZA("Limpeza de Rua"),
    SAUDE("Saude"),
    SEGURANCA_ESCOLAR("Seguranca Escolar"),
    ZELADORIA("Zeladoria"),
    DRENAGEM("Drenagem"),
    ASFALTO("Asfaltamento"),
    ARVORE_PODA("Poda de Arvore"),
    OUTRO("Outro");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
