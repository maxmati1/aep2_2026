package com.cidadaoativo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HistoricoStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final StatusSolicitacao statusAnterior;
    private final StatusSolicitacao statusNovo;
    private final LocalDateTime dataAlteracao;
    private final String responsavel;
    private final String comentario;

    public HistoricoStatus(String id, StatusSolicitacao statusAnterior,
                           StatusSolicitacao statusNovo, String responsavel,
                           String comentario) {
        this.id = id;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.responsavel = responsavel;
        this.comentario = comentario;
        this.dataAlteracao = LocalDateTime.now();
    }

    public String getId() { return id; }
    public StatusSolicitacao getStatusAnterior() { return statusAnterior; }
    public StatusSolicitacao getStatusNovo() { return statusNovo; }
    public LocalDateTime getDataAlteracao() { return dataAlteracao; }
    public String getResponsavel() { return responsavel; }
    public String getComentario() { return comentario; }

    @Override
    public String toString() {
        return String.format("[%s] %s -> %s | Responsavel: %s | Comentario: %s",
                dataAlteracao, statusAnterior.getDescricao(),
                statusNovo.getDescricao(), responsavel, comentario);
    }
}
