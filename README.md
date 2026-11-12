# DAS Chamados

Sistema de gestão de chamados desenvolvido com Java, Spring Boot e frontend em HTML/CSS/JavaScript. O projeto permite cadastrar, consultar, editar, excluir e acompanhar chamados, clientes, técnicos, categorias e comentários.

## Visão geral

Este projeto combina:
- API REST em Java com Spring Boot
- Persistência com JPA/Hibernate
- Banco em memória H2 para desenvolvimento
- Frontend estático em `src/main/resources/static`
- Interface responsiva usando Bootstrap 5

## Funcionalidades

- Cadastro e listagem de chamados
- Alteração de status
- Atribuição de técnico
- Cadastro e gestão de clientes
- Cadastro e gestão de técnicos
- Gestão de categorias
- Inclusão de comentários no chamado
- Dashboard com indicadores gerais
- Operações CRUD para as entidades principais

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Validation
- H2 Database
- Thymeleaf
- Bootstrap 5
- Maven

## Requisitos

- Java 21+
- Maven
- Git
- Navegador web moderno

## Como executar

A partir da pasta raiz do projeto:

Windows:
```bash
mvnw.cmd spring-boot:run
```

Linux/macOS:
```bash
./mvnw spring-boot:run
```

Depois, abra no navegador:
```text
http://localhost:8080
```

## Banco de dados

O projeto utiliza o banco em memória H2 com as seguintes configurações:

- URL: `jdbc:h2:mem:chamadosdb`
- Usuário: `sa`
- Senha: vazia
- Console H2: `http://localhost:8080/h2-console`

## Estrutura do projeto

```text
DAS-CHAMADOS-corrigido/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/sistemas/chamados/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── enums/
│   │   │       ├── exception/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   └── application.properties
│   │   └── test/
│   └── ...
├── pom.xml
├── mvnw
├── mvnw.cmd
├── FRONTEND.md
└── README.md
```

## Observações

- O projeto foi desenvolvido sem autenticação, conforme solicitado.
- O frontend consome diretamente os endpoints REST da API.
- Os dados são reiniciados ao encerrar a aplicação, pois o banco é em memória.
- O Bootstrap e demais assets visuais são carregados por CDN.

## Desenvolvimento

Para rodar em uma IDE como IntelliJ ou Eclipse:

1. Importe a pasta do projeto como projeto Maven.
2. Execute a classe `ChamadosApplication`.
3. Acesse a aplicação no navegador.

## Licença

Este projeto foi criado para fins acadêmicos e de demonstração.
