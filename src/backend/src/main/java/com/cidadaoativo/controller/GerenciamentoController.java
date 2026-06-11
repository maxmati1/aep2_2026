package com.cidadaoativo.controller;

import com.cidadaoativo.domain.Solicitacao;
import com.cidadaoativo.domain.StatusSolicitacao;
import com.cidadaoativo.service.ServicoSolicitacoes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/gerenciamento")
public class GerenciamentoController {

    private final ServicoSolicitacoes servico;

    public GerenciamentoController(ServicoSolicitacoes servico) {
        this.servico = servico;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) StatusSolicitacao status,
            @RequestParam(required = false) String busca,
            Model model) {

        List<Solicitacao> lista;

        if (status != null) {
            lista = servico.listarPorStatus(status);
        } else {
            lista = servico.listarTodas();
        }

        if (busca != null && !busca.isBlank()) {
            String termo = busca.trim().toUpperCase();
            lista = lista.stream()
                    .filter(s -> s.getProtocolo().contains(termo)
                              || s.getLocalizacao().toUpperCase().contains(termo))
                    .toList();
        }

        model.addAttribute("lista", lista);
        model.addAttribute("statusFiltro", status);
        model.addAttribute("busca", busca);
        model.addAttribute("todosStatus", StatusSolicitacao.values());
        return "gerenciamento/index";
    }

    @GetMapping("/{protocolo}")
    public String detalhe(@PathVariable String protocolo, Model model) {
        servico.buscarPorProtocolo(protocolo).ifPresentOrElse(
                s -> {
                    model.addAttribute("solicitacao", s);
                    model.addAttribute("todosStatus", StatusSolicitacao.values());
                },
                () -> model.addAttribute("erro", "Protocolo nao encontrado")
        );
        return "gerenciamento/detalhe";
    }

    @PostMapping("/{protocolo}/status")
    public String atualizarStatus(
            @PathVariable String protocolo,
            @RequestParam StatusSolicitacao novoStatus,
            @RequestParam String responsavel,
            @RequestParam String comentario,
            RedirectAttributes redirectAttrs) {

        try {
            servico.atualizarStatus(protocolo, novoStatus, responsavel, comentario);
            redirectAttrs.addFlashAttribute("sucesso", "Status atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/gerenciamento/" + protocolo;
    }
}
