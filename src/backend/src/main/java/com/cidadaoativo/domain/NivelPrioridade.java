package com.cidadaoativo.domain;

public enum NivelPrioridade {
    BAIXA("Baixa", 30),
    MEDIA("Media", 15),
    ALTA("Alta", 7),
    CRITICA("Critica", 2);

    private final String descricao;
    private final int diasSLA;

    NivelPrioridade(String descricao, int diasSLA) {
        this.descricao = descricao;
        this.diasSLA = diasSLA;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getDiasSLA() {
        return diasSLA;
    }
}
