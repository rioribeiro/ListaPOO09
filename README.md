# ListaPOO09 - Sistema de Comércio Eletrônico

Implementação da atividade em Java com Javelit e arquitetura em três camadas:

- `src/main/java/model`: entidades e DAOs
- `src/main/java/service`: regras e operações do sistema
- `src/main/java/template`: interfaces Javelit
- `src/main/java/App.java`: ponto de entrada

Funcionalidades:

- CRUD de clientes
- CRUD de categorias
- CRUD de produtos
- Reajuste percentual dos preços

## Requisitos

- JDK 21 ou superior
- Maven
- JBang

## Instalar JBang

### PowerShell

No PowerShell, instale e configure o JBang:

```powershell
iex "& { $(iwr https://ps.jbang.dev) } app setup"
```

Se o comando `jbang` não for reconhecido depois da instalação, feche e abra novamente o terminal.

### Bash

No Bash:

```bash
curl -Ls https://sh.jbang.dev | bash -s - app setup
```

Se o comando `jbang` não for reconhecido depois da instalação, abra um novo terminal.

## Compilar e executar a aplicação

Execute os comandos a partir da raiz do projeto. Primeiro, compile as classes do projeto com Maven:

```text
mvn compile
```

Instale o lançador Javelit como uma aplicação JBang:

```text
jbang app install javelit@javelit
```

Inicie o servidor Javelit incluindo as classes compiladas no classpath. Esse classpath é necessário para carregar as classes dos pacotes `model`, `service` e `template`.

### PowerShell

```powershell
javelit run --classpath target/classes src/main/java/App.java
```

### Bash

```bash
javelit run --classpath target/classes src/main/java/App.java
```

O Javelit inicia o servidor local; abra o endereço informado no terminal (por padrão, `http://localhost:8080`). Para encerrar, use `Ctrl+C`.

Para validar a compilação e executar os testes, use:

```text
mvn test
```

> Não é necessário baixar `javelit-0.89.0-all.jar` para a raiz do projeto. O JBang instala o lançador Javelit, e o Maven compila as classes do projeto que são carregadas por `--classpath target/classes`.
