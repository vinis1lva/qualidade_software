# CHANGELOG

## [0.1.0] - 2026-02-24

### Adicionado
- Planejamento de Testes baseado na Aula 02.
- Estrutura de documentação seguindo a IEEE 829.
- Definição de Matriz de Rastreabilidade (RTM).
- Critérios de Entrada, Saída e Suspensão para o ciclo de testes.

---

## Planejamento de Testes (Resumo Aula 02)

Esta seção resume os principais conceitos abordados na Aula 02 sobre Planejamento de Testes, com foco em sua importância, estrutura e ferramentas.

### 1. Visão Geral do Planejamento de Testes
*   **Definição:** Atividade que estabelece o que, como, quando, por quem e com quais critérios de aceite os testes serão realizados.
*   **Importância:** Reduz incertezas, transforma requisitos abstratos em planos executáveis, alinha expectativas entre equipes (QA, Dev, Produto).
*   **Plano de Teste:** Documento vivo que descreve escopo, abordagem, recursos, cronograma, responsabilidades, riscos e critérios para as atividades de teste.
*   **Objetivos Principais:**
    *   Garantir cobertura de requisitos críticos.
    *   Reduzir riscos de negócio antes do release.
    *   Dar previsibilidade para prazo e esforço de QA.
    *   Permitir rastreabilidade ponta a ponta.
*   **Hierarquia de Decomposição:** Requisito -> Use Case -> Test Case -> Dados de Teste.

### 2. IEEE 829: Estrutura de Documentação de Testes
*   **Padrão:** "Standard for Software and System Test Documentation", organiza a documentação de QA.
*   **Artefatos Chave:**
    *   **Test Plan:** Escopo, estratégia, ambiente, riscos, critérios, cronograma.
    *   **Test Design Specification:** Técnicas de projeto, cobertura alvo.
    *   **Test Case Specification:** ID, pré-condições, passos, dados, resultados esperados.
    *   **Test Procedure:** Ordem de execução, script e instruções operacionais.
    *   **Test Log / Incident Report:** Registro da execução, defeitos e evidências.
    *   **Test Summary Report:** Status final, cobertura, riscos residuais e recomendação de release.

### 3. Matriz de Rastreabilidade (RTM)
*   **Propósito:** Tabela que conecta requisitos de negócio, casos de uso, casos de teste e defeitos.
*   **Função:** Garante que todos os requisitos foram cobertos por testes, permite análise de impacto e gestão de mudanças.

### 4. Critérios de Entrada, Saída e Suspensão
*   **Definição:** "Portões" objetivos e pré-acordados que governam o início, a pausa e a conclusão da fase de testes.
*   **Tipos:**
    *   **Entrada (Entry Criteria):** Condições para iniciar os testes.
    *   **Suspensão (Suspension Criteria):** Condições para pausar os testes.
    *   **Saída (Exit Criteria):** Condições para concluir os testes.

---

## Exercício Prático: Correção de Bug nos Testes Unitários

### O Cenário
Ao executar o comando `./mvnw clean install` ou `mvn test`, você notará que o build falhará. O culpado é um erro na lógica de validação de e-mail no `StudentService`.

### Passo a Passo para os Alunos:
1.  **Identificar a Falha:** Execute `mvn test` no terminal.
2.  **Analisar o Relatório:** Observe qual caso de teste no `StudentServiceTest` falhou. (Dica: Olhe para o teste parametrizado `validarEmails`).
3.  **Localizar o Bug:** Abra o arquivo `src/main/java/com/example/educationalqualityproject/service/StudentService.java`.
4.  **Corrigir a Lógica:** Procure o método `isValidEmail`. Note que ele está rejeitando propositalmente e-mails que terminam com `.com`.
5.  **Validar a Correção:** Após ajustar o código, execute `mvn test` novamente. O build deve passar com **SUCCESS**.

---
