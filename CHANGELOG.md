# CHANGELOG

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
