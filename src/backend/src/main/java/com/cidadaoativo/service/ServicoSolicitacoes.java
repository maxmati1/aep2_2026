package com.cidadaoativo.service;

import com.cidadaoativo.domain.Categoria;
import com.cidadaoativo.domain.NivelPrioridade;
import com.cidadaoativo.domain.Solicitacao;
import com.cidadaoativo.domain.StatusSolicitacao;
import com.cidadaoativo.repository.RepositorioSolicitacoes;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicoSolicitacoes {

    private final RepositorioSolicitacoes repositorio;

    public ServicoSolicitacoes(RepositorioSolicitacoes repositorio) {
        this.repositorio = repositorio;
    }

    public Solicitacao criarSolicitacao(String idCidadao, Categoria categoria,
                                        String descricao, String localizacao,
                                        boolean anonimo, NivelPrioridade prioridade) {
        validarCriacao(idCidadao, categoria, descricao, localizacao, prioridade);
        Solicitacao s = new Solicitacao(idCidadao, categoria, descricao,
                localizacao, anonimo, prioridade);
        repositorio.salvar(s);
        return s;
    }

    private void validarCriacao(String idCidadao, Categoria categoria,
                                 String descricao, String localizacao,
                                 NivelPrioridade prioridade) {
        if (idCidadao == null || idCidadao.trim().isEmpty())
            throw new IllegalArgumentException("ID do cidadao e obrigatorio");
        if (categoria == null)
            throw new IllegalArgumentException("Categoria e obrigatoria");
        if (descricao == null || descricao.trim().length() < 10)
            throw new IllegalArgumentException("Descricao deve ter no minimo 10 caracteres");
        if (localizacao == null || localizacao.trim().isEmpty())
            throw new IllegalArgumentException("Localizacao e obrigatoria");
        if (prioridade == null)
            throw new IllegalArgumentException("Prioridade e obrigatoria");
    }

    public Optional<Solicitacao> buscarPorProtocolo(String protocolo) {
        if (protocolo == null || protocolo.trim().isEmpty())
            throw new IllegalArgumentException("Protocolo nao pode ser vazio");
        return repositorio.buscarPorProtocolo(protocolo.trim().toUpperCase());
    }

    public List<Solicitacao> listarSolicitacoesCidadao(String idCidadao) {
        if (idCidadao == null || idCidadao.trim().isEmpty())
            throw new IllegalArgumentException("ID do cidadao e obrigatorio");
        return repositorio.buscarPorCidadao(idCidadao);
    }

    public List<Solicitacao> listarPorStatus(StatusSolicitacao status) {
        Objects.requireNonNull(status, "Status nao pode ser nulo");
        return repositorio.buscarPorStatus(status);
    }

    public List<Solicitacao> listarPorCategoria(Categoria categoria) {
        Objects.requireNonNull(categoria, "Categoria nao pode ser nula");
        return repositorio.buscarPorCategoria(categoria);
    }

    public List<Solicitacao> listarAtrasadas() {
        return repositorio.listarTodas().stream()
                .filter(Solicitacao::estaAtrasada)
                .sorted(Comparator.comparing(Solicitacao::getDataPrazo))
                .collect(Collectors.toList());
    }

    public void atualizarStatus(String protocolo, StatusSolicitacao novoStatus,
                                String responsavel, String comentario) {
        Solicitacao s = repositorio.buscarPorProtocolo(protocolo)
                .orElseThrow(() -> new NoSuchElementException("Solicitacao nao encontrada: " + protocolo));
        s.atualizarStatus(novoStatus, responsavel, comentario);
        repositorio.atualizar(s);
    }

    public List<Solicitacao> listarTodas() {
        return repositorio.listarTodas();
    }

    public long contarAbertas() {
        return repositorio.contarPorStatus(StatusSolicitacao.ABERTO);
    }

    public long contarResolvidas() {
        return repositorio.contarPorStatus(StatusSolicitacao.RESOLVIDO)
                + repositorio.contarPorStatus(StatusSolicitacao.ENCERRADO);
    }

    public long contarEmExecucao() {
        return repositorio.contarPorStatus(StatusSolicitacao.EM_EXECUCAO);
    }

    public long contarTriagem() {
        return repositorio.contarPorStatus(StatusSolicitacao.TRIAGEM);
    }

    public int contarTotal() {
        return repositorio.contar();
    }

    public Map<String, Long> contagemPorCategoria() {
        return repositorio.listarTodas().stream()
                .collect(Collectors.groupingBy(
                        s -> s.getCategoria().getDescricao(),
                        Collectors.counting()
                ));
    }

    public Map<String, Long> contagemPorStatus() {
        return repositorio.listarTodas().stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStatus().getDescricao(),
                        Collectors.counting()
                ));
    }

    public double percentualResolvidasNoPrazo() {
        List<Solicitacao> resolvidas = repositorio.listarTodas().stream()
                .filter(s -> s.getStatus() == StatusSolicitacao.RESOLVIDO
                          || s.getStatus() == StatusSolicitacao.ENCERRADO)
                .collect(Collectors.toList());

        if (resolvidas.isEmpty()) return 0.0;

        long noPrazo = resolvidas.stream()
                .filter(s -> !s.estaAtrasada())
                .count();

        return (noPrazo * 100.0) / resolvidas.size();
    }
}
