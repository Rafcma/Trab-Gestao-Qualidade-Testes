# Sistema de Gerenciamento Bibliográfico

Sistema web completo para gerenciamento de biblioteca desenvolvido com Spring Boot, permitindo o cadastro e controle de autores, editoras e livros com interface web intuitiva e API REST.

## Sobre o Projeto

Este sistema foi desenvolvido como projeto acadêmico para demonstrar conceitos avançados de desenvolvimento web com Java Spring Boot, incluindo persistência de dados, API RESTful, queries customizadas e testes automatizados com JUnit 5.

## Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **MySQL 8.0** - Banco de dados de produção
- **H2 Database** - Banco de dados em memória para testes
- **Thymeleaf** - Template engine para views
- **JUnit 5** - Framework de testes
- **Maven** - Gerenciamento de dependências

## Funcionalidades

- **Gestão de Autores**: CRUD completo com validação de integridade referencial
- **Gestão de Editoras**: Cadastro e gerenciamento de editoras
- **Gestão de Livros**: Controle de acervo com relacionamentos entre autores e editoras
- **Queries Customizadas**: JPQL e SQL nativo para consultas avançadas
- **API REST**: Endpoints completos para integração externa
- **Interface Web**: Sistema completo com navegação intuitiva

## Testes Automatizados

O projeto implementa uma suíte completa de testes automatizados utilizando JUnit 5, seguindo as melhores práticas de desenvolvimento orientado a testes.

### Cobertura de Testes

O sistema possui **10 testes automatizados** cobrindo:

- **Controllers REST** (5 testes): Validação de endpoints de criação, listagem e deleção
- **Repositories** (2 testes): Verificação de queries JPQL e SQL nativas customizadas
- **Lógica de Negócio** (3 testes): Teste de atualizações, buscas e relacionamentos entre entidades

### Estrutura dos Testes

Todos os testes seguem o padrão AAA (Arrange-Act-Assert):

1. **Arrange (Preparar)**: Configuração dos dados de teste
2. **Act (Agir)**: Execução da ação que será testada
3. **Assert (Verificar)**: Validação dos resultados esperados

### Tecnologias de Teste

- **@SpringBootTest**: Carrega contexto completo do Spring para testes de integração
- **@ActiveProfiles("test")**: Ativa perfil de teste com banco H2 em memória
- **@Transactional**: Garante rollback automático após cada teste
- **@DisplayName**: Nomes descritivos em português para melhor legibilidade

### Como Executar os Testes

#### Via Maven Wrapper (Windows)

\`\`\`bash
# Executar todos os testes
./mvnw test

# Executar uma classe específica
./mvnw test -Dtest=AutorControllerTest

# Executar um teste específico
./mvnw test -Dtest=AutorControllerTest#testCriarAutorComSucesso

# Gerar relatório HTML
./mvnw clean test site
\`\`\`

#### Via IntelliJ IDEA

1. Abra o projeto no IntelliJ IDEA
2. Navegue até `src/test/java`
3. Clique com botão direito na pasta ou classe de teste
4. Selecione "Run Tests" ou "Run 'NomeDaClasse'"

#### Via Eclipse

1. Abra o projeto no Eclipse
2. Clique com botão direito no projeto
3. Selecione "Run As" > "JUnit Test"

### Relatórios de Teste

Após executar os testes com Maven, relatórios detalhados são gerados em:

- **Console**: Resultado imediato com contagem de testes executados
- **XML**: `target/surefire-reports/*.xml` (para CI/CD)
- **HTML**: `target/site/surefire-report.html` (relatório visual)

### Documentação Interativa

O sistema possui uma página web dedicada à documentação dos testes acessível em `/testes` quando a aplicação está em execução. Esta página apresenta:

- Descrição detalhada de cada teste
- Passos executados em cada cenário
- Resultados esperados
- Exemplos de código
- Guia completo de execução

## Configuração do Ambiente

### Pré-requisitos

- Java 21 ou superior
- MySQL 8.0
- Maven 3.8+ (ou usar o wrapper incluído)

### Configuração do Banco de Dados

\`\`\`sql
CREATE DATABASE biblioteca;
CREATE USER 'bibliouser'@'localhost' IDENTIFIED BY 'senha123';
GRANT ALL PRIVILEGES ON biblioteca.* TO 'bibliouser'@'localhost';
FLUSH PRIVILEGES;
\`\`\`

### Executar a Aplicação

\`\`\`bash
# Via Maven Wrapper
./mvnw spring-boot:run

# Via JAR compilado
./mvnw clean package
java -jar target/biblioteca-api-0.0.1-SNAPSHOT.jar
\`\`\`

### Acessar a Aplicação

- **Interface Web**: http://localhost:8080
- **API REST**: http://localhost:8080/api/*
- **Documentação de Testes**: http://localhost:8080/testes
- **Documentação Técnica**: http://localhost:8080/dev

## Estrutura do Projeto

\`\`\`
biblioteca-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/biblioteca_api/
│   │   │   ├── config/          # Configurações e inicializadores
│   │   │   ├── controller/      # Controllers REST e View
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── model/           # Entidades JPA (Autor, Editora, Livro)
│   │   │   └── repository/      # Repositories Spring Data JPA
│   │   └── resources/
│   │       ├── static/css/      # Arquivos CSS
│   │       ├── templates/       # Views Thymeleaf
│   │       └── application.properties
│   └── test/
│       ├── java/                # Classes de teste JUnit
│       │   ├── controller/      # Testes de controllers
│       │   └── repository/      # Testes de repositories
│       └── resources/
│           └── application-test.properties
├── pom.xml                      # Dependências Maven
└── README.md                    # Este arquivo
\`\`\`

## Endpoints da API

### Autores
- `GET /api/autores` - Listar todos os autores
- `POST /api/autores` - Criar novo autor
- `GET /api/autores/{id}` - Buscar autor por ID
- `PUT /api/autores/{id}` - Atualizar autor
- `DELETE /api/autores/{id}` - Deletar autor

### Editoras
- `GET /api/editoras` - Listar todas as editoras
- `POST /api/editoras` - Criar nova editora
- `GET /api/editoras/{id}` - Buscar editora por ID
- `PUT /api/editoras/{id}` - Atualizar editora
- `DELETE /api/editoras/{id}` - Deletar editora

### Livros
- `GET /api/livros` - Listar todos os livros
- `POST /api/livros` - Criar novo livro
- `GET /api/livros/{id}` - Buscar livro por ID
- `PUT /api/livros/{id}` - Atualizar livro
- `DELETE /api/livros/{id}` - Deletar livro
- `GET /api/livros/search?titulo={titulo}` - Buscar por título

## Boas Práticas Implementadas

- **Separação de responsabilidades**: Controllers, Services, Repositories e Models bem definidos
- **Validação de integridade**: Impede deleção de entidades com relacionamentos ativos
- **Banco de dados isolado para testes**: H2 em memória evita impacto no banco de produção
- **Testes independentes**: Cada teste cria e limpa seus próprios dados
- **Nomenclatura descritiva**: Nomes em português facilitam compreensão
- **Padrão AAA**: Estrutura clara de testes (Arrange-Act-Assert)
- **Rollback automático**: Transações garantem limpeza após testes
- **Documentação completa**: README e páginas web explicativas

## Autor

Desenvolvido como projeto acadêmico para demonstrar domínio de Spring Boot, JPA, Thymeleaf e testes automatizados com JUnit 5.

## Licença

Este projeto é de uso acadêmico e educacional.
- Programadores:
- Rafael; Lucca; Kawe;
