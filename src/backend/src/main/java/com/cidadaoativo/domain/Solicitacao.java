package com.cidadaoativo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Solicitacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String protocolo;
    private final String idCidadao;
    private final Categoria categoria;
    private final String descricao;
    private final String localizacao;
    private String nomeAnexo;
    private final boolean anonimo;
    private final NivelPrioridade prioridade;
    private StatusSolicitacao status;
    private final LocalDateTime dataCriacao;
    private final LocalDateTime dataPrazo;
    private final List<HistoricoStatus> historico;

    public Solicitacao(String idCidadao, Categoria categoria, String descricao,
                       String localizacao, boolean anonimo, NivelPrioridade prioridade) {
        this.protocolo = gerarProtocolo();
        this.idCidadao = idCidadao;
        this.categoria = categoria;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.anonimo = anonimo;
        this.prioridade = prioridade;
        this.status = StatusSolicitacao.ABERTO;
        this.dataCriacao = LocalDateTime.now();
        this.dataPrazo = dataCriacao.plusDays(prioridade.getDiasSLA());
        this.historico = new ArrayList<>();
    }

    private String gerarProtocolo() {
        return "CA-" + System.currentTimeMillis() + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void adicionarHistorico(HistoricoStatus entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("Historico nao pode ser nulo");
        }
        this.historico.add(entrada);
    }

    public void atualizarStatus(StatusSolicitacao novoStatus, String responsavel, String comentario) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("Status nao pode ser nulo");
        }
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("Comentario e obrigatorio");
        }
        HistoricoStatus entrada = new HistoricoStatus(
                UUID.randomUUID().toString(),
                this.status,
                novoStatus,
                responsavel,
                comentario
        );
        this.status = novoStatus;
        adicionarHistorico(entrada);
    }

    public boolean estaAtrasada() {
        return LocalDateTime.now().isAfter(dataPrazo) &&
                status != StatusSolicitacao.RESOLVIDO &&
                status != StatusSolicitacao.ENCERRADO;
    }

    public String getIdentificacaoCidadao() {
        return anonimo ? "ANONIMO" : idCidadao;
    }

    public String getProtocolo() { return protocolo; }
    public String getIdCidadao() { return idCidadao; }
    public Categoria getCategoria() { return categoria; }
    public String getDescricao() { return descricao; }
    public String getLocalizacao() { return localizacao; }
    public String getNomeAnexo() { return nomeAnexo; }
    public boolean isAnonimo() { return anonimo; }
    public NivelPrioridade getPrioridade() { return prioridade; }
    public StatusSolicitacao getStatus() { return status; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataPrazo() { return dataPrazo; }
    public List<HistoricoStatus> getHistorico() { return new ArrayList<>(historico); }

    public void setNomeAnexo(String nomeAnexo) { this.nomeAnexo = nomeAnexo; }

    @Override
    public String toString() {
        return String.format(
                "Protocolo: %s | Categoria: %s | Status: %s | Prioridade: %s | Criada: %s",
                protocolo, categoria.getDescricao(), status.getDescricao(),
                prioridade.getDescricao(), dataCriacao);
    }
}
