# CHANGELOG

## [0.4.0] - 2026-03-29

### Aula: Testes Unitarios no CI

#### Objetivo
Explicar como os testes automatizados do projeto funcionam e como o pipeline de CI pode evoluir para validar melhor build, testes e cobertura.

#### Como funcionam os testes de unidade
Este projeto nao usa Jest. Como se trata de uma aplicacao Java com Spring Boot, os testes automatizados sao executados com Maven e `spring-boot-starter-test`, que traz a stack de testes baseada em JUnit 5, Mockito e Spring Test.

Os testes unitarios estao concentrados principalmente em:

- `src/test/java/com/example/educationalqualityproject/service/StudentServiceTest.java`
- `src/test/java/com/example/educationalqualityproject/service/TeacherServiceTest.java`
- `src/test/java/com/example/educationalqualityproject/controller/HomeControllerTest.java`
- `src/test/java/com/example/educationalqualityproject/controller/StudentControllerTest.java`
- `src/test/java/com/example/educationalqualityproject/controller/TeacherControllerTest.java`

Em termos praticos, o fluxo e o seguinte:

1. O comando `mvn test` compila o codigo de producao e os testes.
2. O Surefire localiza classes com sufixo `*Test`.
3. O JUnit executa os cenarios automatizados.
4. Quando o teste depende de contexto Spring ou acesso ao MongoDB de teste, a aplicacao sobe com a configuracao de `src/test/resources/application.properties`.
5. Em `mvn verify`, o JaCoCo gera o relatorio de cobertura em `target/site/jacoco/`.

#### Como o `ci.yml` anterior podia ser melhorado
O workflow anterior funcionava como exemplo inicial, mas tinha algumas limitacoes importantes:

- Executava apenas em `push`, sem validar `pull_request`.
- Havia inconsistência de versao Java entre build local e pipeline.
- Separava build e teste, mas sem ampliar a confiabilidade da validacao.
- Nao publicava relatorios de testes nem cobertura.
- Nao cancelava execucoes antigas da mesma branch.
- Nao validava compatibilidade em mais de uma versao de Java.

#### Como o novo `ci.yml` evolui a pipeline
O novo workflow foi estruturado para refletir um CI mais proximo de ambiente real:

1. Executa em `push`, `pull_request` e `workflow_dispatch`.
2. Cancela pipelines antigas da mesma branch com `concurrency`.
3. Padroniza a execucao dos testes em Java 21.
4. Provisiona MongoDB nos jobs que executam testes.
5. Executa `mvn test` para feedback rapido de regressao.
6. Executa `mvn verify` em um job dedicado para gerar cobertura com JaCoCo.
7. Publica artifacts de cobertura, relatorios Surefire e o `.jar` empacotado.

#### O que acontece no GitHub Actions
Quando alguem faz `push` na branch, abre um `pull request` ou dispara manualmente o workflow, o GitHub Actions inicia o arquivo `.github/workflows/ci.yml` e executa a pipeline nesta ordem:

1. O job `test` sobe um runner `ubuntu-latest`.
2. Nesse runner, o workflow faz `checkout` do repositorio.
3. Em seguida, instala e configura o Java 21 com cache Maven.
4. O job sobe um container de servico `mongo:8`, usado pelos testes que dependem de banco.
5. O passo `Run unit and integration tests` executa `./mvnw -B -ntp clean test`.
6. Nesse momento, o Maven recompila o projeto, compila os testes, executa o Surefire e roda os cenarios unitarios, web MVC e E2E.
7. Se todos os testes passarem, o job `test` termina com sucesso.
8. Somente depois disso o job `verify-coverage` e liberado, porque ele depende de `needs: test`.
9. O segundo job repete o ambiente controlado: checkout, Java 21, Maven cache e MongoDB.
10. O passo `Run full verification with coverage` executa `./mvnw -B -ntp clean verify`.
11. Nessa etapa, alem da suite de testes, o JaCoCo gera o relatorio HTML de cobertura e o Maven empacota o `.jar` final da aplicacao.
12. Ao final, o workflow publica tres artifacts:
13. `jacoco-report`: relatorio de cobertura em HTML.
14. `surefire-reports`: relatorios detalhados de execucao dos testes.
15. `app-jar`: arquivo empacotado da aplicacao para inspecao ou download.

Na pratica, isso significa que o GitHub Actions esta validando tres coisas importantes em sequencia: se o projeto compila em Java 21, se os testes passam contra um MongoDB real de apoio e se o build final com cobertura tambem fecha sem regressao.

#### Competencias Desenvolvidas
- Diferenciacao entre testes unitarios em Java e ferramentas de teste de outros ecossistemas
- Leitura de suites de teste com JUnit e Spring Boot Test
- Evolucao de pipelines de CI com GitHub Actions
- Publicacao de relatorios e artifacts de validacao
- Padronizacao de ambiente de build e testes

#### Arquivos Relacionados
- Workflow de CI: `.github/workflows/ci.yml`
- Configuracao Maven: `pom.xml`
- Suite de testes: `src/test/java/com/example/educationalqualityproject/`
- Configuracao de testes: `src/test/resources/application.properties`

---

## [0.3.0] - 2026-03-15

### Aula: Continuous Integration (CI)

#### Objetivo
Apresentar o conceito de Continuous Integration e mostrar como automatizar build e testes com GitHub Actions.

#### O que e Continuous Integration
Continuous Integration e a pratica de integrar alteracoes no repositorio com frequencia, executando validacoes automaticas a cada `push` ou `pull request`. O objetivo e detectar falhas cedo, reduzir regressões e garantir que o projeto continue compilando e passando nos testes enquanto evolui.

#### Etapas do `ci.yml`
O workflow `.github/workflows/ci.yml` foi estruturado para executar duas etapas principais:

1. **Build**
   - Executa em `ubuntu-latest`
   - Faz o checkout do codigo com `actions/checkout@v4`
   - Configura o Java 21 com `actions/setup-java@v4`
   - Executa `./mvnw package -DskipTests` para compilar e empacotar a aplicacao
   - Publica o `.jar` gerado como artifact com `actions/upload-artifact@v4`

2. **Test**
   - Executa apos o build com `needs: build`
   - Provisiona um servico MongoDB para suportar os testes
   - Faz novamente o checkout do codigo
   - Configura o Java 21 e reutiliza cache Maven
   - Baixa o artifact gerado no build com `actions/download-artifact@v4`
   - Executa `./mvnw test` para validar o comportamento da aplicacao

#### Competências Desenvolvidas
- Compreensao do conceito de Continuous Integration
- Leitura e interpretacao de workflows do GitHub Actions
- Automacao de build e execucao de testes
- Uso de artifacts entre jobs
- Validacao automatica de aplicacoes Java com Maven

#### Arquivos Relacionados
- Workflow de CI: `.github/workflows/ci.yml`
- Build Maven: `pom.xml`
- Testes automatizados: `src/test/java/com/example/educationalqualityproject/`

---

## [0.2.0] - 2026-02-25

### Atualizado
- Cobertura de testes expandida com novos cenarios unitarios, web MVC e end-to-end.
- Configuracao de cobertura com JaCoCo adicionada ao Maven.
- Relatorio HTML de cobertura gerado em `target/site/jacoco/index.html`.
- Documentacao de diagramas UML de sequencia dos testes E2E criada em `docs/e2e-sequence-diagrams.md`.
- Remocao do arquivo `rtm.md`.

### Novos Testes
- `src/test/java/com/example/educationalqualityproject/service/TeacherServiceTest.java`
  - Cobre `getAllTeachers`, `getTeacherById`, `saveTeacher`, `deleteTeacher`, `existsByEmail`.
- `src/test/java/com/example/educationalqualityproject/controller/StudentControllerTest.java`
  - Cobre listagem, formulario de criacao, criacao, edicao, atualizacao e exclusao.
- `src/test/java/com/example/educationalqualityproject/controller/TeacherControllerTest.java`
  - Cobre listagem, formulario de criacao, criacao, edicao, atualizacao e exclusao.
- `src/test/java/com/example/educationalqualityproject/e2e/StudentApiE2ETest.java`
  - Cobre fluxos E2E de API para lista, criacao com sucesso, conflito por email, atualizacao inexistente e exclusao.
- `src/test/java/com/example/educationalqualityproject/e2e/TeacherApiE2ETest.java`
  - Cobre fluxos E2E de API para lista, criacao com sucesso, conflito por email, atualizacao inexistente e exclusao.
- `src/test/java/com/example/educationalqualityproject/e2e/StudentApiE2ETest.java`
  - Adicionado 1 teste E2E de desafio (`challengeTestShouldFailOnPurpose`) quebrado propositalmente, para o aluno corrigir a expectativa de status HTTP.
  - Comando para executar somente o teste quebrado: `mvn -q -Dtest=StudentApiE2ETest#challengeTestShouldFailOnPurpose test`

### Ajustes Tecnicos
- `pom.xml`
  - Inclusao do plugin `jacoco-maven-plugin` com `prepare-agent` e `report` na fase `verify`.

### Execucao e Resultado
- Comando executado: `mvn clean verify`
- Status: **SUCESSO**
- Cobertura global (JaCoCo):
  - Instrucoes: **74.23%** (484/652)
  - Branches: **30.00%** (12/40)
  - Metodos: **93.24%** (69/74)
