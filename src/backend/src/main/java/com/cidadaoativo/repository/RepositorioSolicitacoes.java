package com.cidadaoativo.repository;

import com.cidadaoativo.domain.Categoria;
import com.cidadaoativo.domain.NivelPrioridade;
import com.cidadaoativo.domain.Solicitacao;
import com.cidadaoativo.domain.StatusSolicitacao;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class RepositorioSolicitacoes {

    private final Map<String, Solicitacao> solicitacoes = new ConcurrentHashMap<>();

    public void salvar(Solicitacao solicitacao) {
        if (solicitacao == null) {
            throw new IllegalArgumentException("Solicitacao nao pode ser nula");
        }
        solicitacoes.put(solicitacao.getProtocolo(), solicitacao);
    }

    public Optional<Solicitacao> buscarPorProtocolo(String protocolo) {
        if (protocolo == null) return Optional.empty();
        return Optional.ofNullable(solicitacoes.get(protocolo));
    }

    public List<Solicitacao> buscarPorCidadao(String idCidadao) {
        return solicitacoes.values().stream()
                .filter(s -> s.getIdCidadao().equals(idCidadao))
                .sorted(Comparator.comparing(Solicitacao::getDataCriacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Solicitacao> buscarPorStatus(StatusSolicitacao status) {
        return solicitacoes.values().stream()
                .filter(s -> s.getStatus() == status)
                .sorted(Comparator.comparing(Solicitacao::getDataCriacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Solicitacao> buscarPorCategoria(Categoria categoria) {
        return solicitacoes.values().stream()
                .filter(s -> s.getCategoria() == categoria)
                .sorted(Comparator.comparing(Solicitacao::getDataCriacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Solicitacao> buscarPorPrioridade(NivelPrioridade prioridade) {
        return solicitacoes.values().stream()
                .filter(s -> s.getPrioridade() == prioridade)
                .collect(Collectors.toList());
    }

    public void atualizar(Solicitacao solicitacao) {
        if (solicitacao == null) {
            throw new IllegalArgumentException("Solicitacao nao pode ser nula");
        }
        if (!solicitacoes.containsKey(solicitacao.getProtocolo())) {
            throw new NoSuchElementException("Solicitacao nao encontrada: " + solicitacao.getProtocolo());
        }
        solicitacoes.put(solicitacao.getProtocolo(), solicitacao);
    }

    public List<Solicitacao> listarTodas() {
        return solicitacoes.values().stream()
                .sorted(Comparator.comparing(Solicitacao::getDataCriacao).reversed())
                .collect(Collectors.toList());
    }

    public int contar() {
        return solicitacoes.size();
    }

    public long contarPorStatus(StatusSolicitacao status) {
        return solicitacoes.values().stream()
                .filter(s -> s.getStatus() == status)
                .count();
    }

    public void limpar() {
        solicitacoes.clear();
    }
}
