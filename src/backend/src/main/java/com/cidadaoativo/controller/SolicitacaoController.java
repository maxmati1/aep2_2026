package com.cidadaoativo.controller;

import com.cidadaoativo.domain.Categoria;
import com.cidadaoativo.domain.NivelPrioridade;
import com.cidadaoativo.domain.Solicitacao;
import com.cidadaoativo.domain.StatusSolicitacao;
import com.cidadaoativo.service.ServicoSolicitacoes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final ServicoSolicitacoes servico;

    public SolicitacaoController(ServicoSolicitacoes servico) {
        this.servico = servico;
    }

    @GetMapping("/nova")
    public String formNova(Model model) {
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("prioridades", NivelPrioridade.values());
        return "solicitacoes/nova";
    }

    @PostMapping("/nova")
    public String criarSolicitacao(
            @RequestParam String idCidadao,
            @RequestParam Categoria categoria,
            @RequestParam String descricao,
            @RequestParam String localizacao,
            @RequestParam(defaultValue = "false") boolean anonimo,
            @RequestParam NivelPrioridade prioridade,
            RedirectAttributes redirectAttrs) {

        try {
            Solicitacao s = servico.criarSolicitacao(
                    idCidadao, categoria, descricao, localizacao, anonimo, prioridade);
            redirectAttrs.addFlashAttribute("sucesso",
                    "Solicitacao registrada! Protocolo: " + s.getProtocolo());
            redirectAttrs.addFlashAttribute("protocolo", s.getProtocolo());
            return "redirect:/solicitacoes/status?protocolo=" + s.getProtocolo();
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("erro", e.getMessage());
            return "redirect:/solicitacoes/nova";
        }
    }

    @GetMapping("/status")
    public String verStatus(@RequestParam(required = false) String protocolo, Model model) {
        if (protocolo != null && !protocolo.isBlank()) {
            Optional<Solicitacao> opt = servico.buscarPorProtocolo(protocolo);
            opt.ifPresent(s -> model.addAttribute("solicitacao", s));
            if (opt.isEmpty()) {
                model.addAttribute("erro", "Protocolo nao encontrado: " + protocolo);
            }
        }
        model.addAttribute("protocolo", protocolo);
        model.addAttribute("todosStatus", StatusSolicitacao.values());
        return "solicitacoes/status";
    }

    @GetMapping("/buscar")
    public String formBuscar() {
        return "solicitacoes/buscar";
    }

    @PostMapping("/buscar")
    public String buscarPorCidadao(@RequestParam String idCidadao, Model model) {
        try {
            List<Solicitacao> lista = servico.listarSolicitacoesCidadao(idCidadao);
            model.addAttribute("lista", lista);
            model.addAttribute("idCidadao", idCidadao);
            model.addAttribute("buscaRealizada", true);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "solicitacoes/buscar";
    }
}
