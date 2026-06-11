package com.cidadaoativo.controller;

import com.cidadaoativo.service.ServicoSolicitacoes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estatisticas")
public class EstatisticaController {

    private final ServicoSolicitacoes servico;

    public EstatisticaController(ServicoSolicitacoes servico) {
        this.servico = servico;
    }

    @GetMapping
    public String verEstatisticas(Model model) {
        model.addAttribute("total", servico.contarTotal());
        model.addAttribute("abertas", servico.contarAbertas());
        model.addAttribute("emExecucao", servico.contarEmExecucao());
        model.addAttribute("resolvidas", servico.contarResolvidas());
        model.addAttribute("triagem", servico.contarTriagem());
        model.addAttribute("atrasadas", servico.listarAtrasadas().size());
        model.addAttribute("percentualNoPrazo",
                String.format("%.0f", servico.percentualResolvidasNoPrazo()));
        model.addAttribute("porCategoria", servico.contagemPorCategoria());
        model.addAttribute("porStatus", servico.contagemPorStatus());
        return "estatisticas/index";
    }
}
