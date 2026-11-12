# DAS Chamados — Front-end integrado

## O que foi adicionado
- Interface responsiva com Bootstrap 5 e JavaScript puro (sem React ou Angular).
- Painel com indicadores e chamados recentes.
- Listagem, criação, edição e exclusão de chamados, clientes, técnicos e categorias.
- Visualização do chamado, alteração de status, atribuição de técnico e inclusão de comentários.
- Listagem, criação, edição e exclusão de comentários.
- O front-end consome diretamente os endpoints REST já existentes. Nenhuma rota ou DTO do backend foi alterado.

## Como executar
1. Abra a pasta raiz do projeto no terminal.
2. Execute `.\mvnw.cmd spring-boot:run` no Windows ou `./mvnw spring-boot:run` no Linux/macOS.
3. Acesse `http://localhost:8080`.

Também é possível importar o projeto como **Existing Maven Project** na IDE e executar `ChamadosApplication`.

## Observações
- O banco configurado é H2 em memória (`jdbc:h2:mem:chamadosdb`). Os dados são reiniciados quando a aplicação é encerrada.
- O Bootstrap e os ícones são carregados por CDN; é necessária conexão com a internet para esses recursos visuais.
- Não foi criado login/autenticação, conforme solicitado.
- A aplicação continua usando Maven, Java 21 e o `pom.xml` original.
