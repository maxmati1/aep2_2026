# CidadaoAtivo

Sistema web de registro e acompanhamento de ocorrencias urbanas, desenvolvido com Spring Boot e Thymeleaf.

## Repositorio em Memoria

O sistema utiliza armazenamento em memoria para gerenciamento dos dados durante a execucao da aplicacao.

## Como Executar

```bash
cd src/backend
./mvnw spring-boot:run
```

Acesse:

```text
http://localhost:8080
```

## Telas

| Rota                   | Descricao                      |
| ---------------------- | ------------------------------ |
| `/solicitacoes/nova`   | Registrar nova ocorrencia      |
| `/solicitacoes/status` | Consultar status por protocolo |
| `/solicitacoes/buscar` | Buscar solicitacoes por CPF/ID |
| `/estatisticas`        | Painel de metricas             |
| `/gerenciamento`       | Painel do atendente/gestor     |

## Arquitetura

O projeto segue o padrao em camadas do Spring:

* **domain** — entidades e enums do negocio
* **repository** — persistencia em memoria
* **service** — regras de negocio e validacoes
* **controller** — recepcao HTTP e delegacao ao service

## Dados de Demonstracao

Ao iniciar a aplicacao, o `DataSeeder` popula automaticamente usuarios e solicitacoes de exemplo para facilitar a navegacao e demonstracao das funcionalidades do sistema.
