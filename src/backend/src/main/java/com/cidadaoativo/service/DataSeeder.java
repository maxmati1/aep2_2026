package com.cidadaoativo.service;

import com.cidadaoativo.domain.Categoria;
import com.cidadaoativo.domain.NivelPrioridade;
import com.cidadaoativo.domain.Solicitacao;
import com.cidadaoativo.domain.StatusSolicitacao;
import com.cidadaoativo.domain.TipoUsuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ServicoSolicitacoes servicoSolicitacoes;
    private final ServicoUsuarios servicoUsuarios;

    public DataSeeder(ServicoSolicitacoes servicoSolicitacoes, ServicoUsuarios servicoUsuarios) {
        this.servicoSolicitacoes = servicoSolicitacoes;
        this.servicoUsuarios = servicoUsuarios;
    }

    @Override
    public void run(String... args) throws Exception {
        servicoUsuarios.criarUsuario("Ana Silva", "ana@email.com", "41999990001",
                "Rua das Flores, 100", "Centro", TipoUsuario.CIDADAO);
        servicoUsuarios.criarUsuario("Carlos Pereira", "carlos@prefeitura.gov.br", "41999990002",
                "Av. Principal, 200", "Centro", TipoUsuario.ATENDENTE);
        servicoUsuarios.criarUsuario("Maria Gestora", "maria@prefeitura.gov.br", "41999990003",
                "Rua da Gestao, 1", "Centro", TipoUsuario.GESTOR);

        Solicitacao s1 = servicoSolicitacoes.criarSolicitacao(
                "ana@email.com", Categoria.ILUMINACAO,
                "Poste apagado na esquina da Rua X com Y ha 5 dias causando inseguranca",
                "Bairro Centro", false, NivelPrioridade.ALTA);

        Solicitacao s2 = servicoSolicitacoes.criarSolicitacao(
                "ana@email.com", Categoria.BURACO_VIA,
                "Buraco grande na Avenida Principal proximo ao numero 150 risco de acidente",
                "Bairro Jardim", false, NivelPrioridade.CRITICA);

        Solicitacao s3 = servicoSolicitacoes.criarSolicitacao(
                "cidadao-anonimo", Categoria.LIMPEZA,
                "Lixo acumulado no terreno baldio da Rua das Palmeiras sem coleta ha semanas",
                "Bairro Norte", true, NivelPrioridade.MEDIA);

        servicoSolicitacoes.criarSolicitacao(
                "ana@email.com", Categoria.ARVORE_PODA,
                "Galho de arvore caido sobre a calcada bloqueando passagem de pedestres",
                "Bairro Sul", false, NivelPrioridade.MEDIA);

        servicoSolicitacoes.atualizarStatus(s1.getProtocolo(), StatusSolicitacao.TRIAGEM,
                "carlos@prefeitura.gov.br", "Solicitacao recebida e classificada para triagem");
        servicoSolicitacoes.atualizarStatus(s1.getProtocolo(), StatusSolicitacao.EM_EXECUCAO,
                "carlos@prefeitura.gov.br", "Equipe de manutencao eletrica acionada");
        servicoSolicitacoes.atualizarStatus(s1.getProtocolo(), StatusSolicitacao.RESOLVIDO,
                "carlos@prefeitura.gov.br", "Lampada substituida e poste normalizado");

        servicoSolicitacoes.atualizarStatus(s2.getProtocolo(), StatusSolicitacao.TRIAGEM,
                "carlos@prefeitura.gov.br", "Buraco confirmado - alta prioridade");
        servicoSolicitacoes.atualizarStatus(s2.getProtocolo(), StatusSolicitacao.EM_EXECUCAO,
                "carlos@prefeitura.gov.br", "Equipe de obras a caminho");

        servicoSolicitacoes.atualizarStatus(s3.getProtocolo(), StatusSolicitacao.TRIAGEM,
                "carlos@prefeitura.gov.br", "Agendando coleta especial");

        System.out.println("CidadaoAtivo: dados de demonstracao carregados.");
        System.out.println("Total de solicitacoes: " + servicoSolicitacoes.contarTotal());
    }
}
