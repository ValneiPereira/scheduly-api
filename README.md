# 📅 Scheduly API  
API de agendamento de serviços para salões de beleza, manicures e profissionais autônomos.  
Desenvolvido em **Java 17** com **Spring Boot 3.4.12**.

---

## 🚀 Visão Geral

O **Scheduly** é uma plataforma de agendamento SaaS voltada para prestadores de serviços como manicures, pedicures e profissionais de estética.  
A API fornece recursos para:

- Cadastro de clientes  
- Cadastro de profissionais  
- Cadastro de serviços  
- Gerenciamento de agendamentos  
- Notificações automáticas  
- Consulta de CEP via API pública  
- Autenticação segura com JWT  

---

## 🏗 Arquitetura

A API segue o padrão **REST**, com divisão em camadas seguindo os princípios de **Clean Architecture**:

### 📂 Estrutura de Pastas

```
src/main/java/com/scheduly/api/
├── config/              # Configurações (Security, Swagger, Application)
├── domain/              # Entidades de domínio e interfaces de repositórios
│   ├── booking/         # Agendamentos
│   ├── client/          # Clientes
│   ├── professional/    # Profissionais
│   ├── service/         # Serviços
│   └── notification/    # Notificações
├── application/         # Casos de uso (Use Cases)
│   ├── booking/         # Criar, cancelar, listar agendamentos
│   ├── client/          # CRUD de clientes
│   ├── professional/    # CRUD de profissionais
│   ├── service/         # CRUD de serviços
│   ├── auth/            # Login e refresh token
│   └── cep/             # Consulta de CEP
├── infrastructure/      # Implementações de infraestrutura
│   ├── persistence/     # Entidades JPA e repositórios
│   ├── auth/            # JWT Provider, Filters, UserDetails
│   └── external/        # Integração com APIs externas (ViaCEP)
└── web/                 # Camada de apresentação
    ├── controllers/     # REST Controllers
    ├── dtos/            # Data Transfer Objects
    └── handlers/        # Exception Handlers
```

---

## 🛠 Tecnologias

- **Java 17**
- **Spring Boot 3.4.12**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados relacional
- **Springdoc OpenAPI 2.8.0** - Documentação da API (Swagger)
- **OpenAPI Generator** - Geração automática de interfaces
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

---

## 📋 Pré-requisitos

- **JDK 17** ou superior
- **Maven 3.6+**
- **PostgreSQL** (opcional, pode rodar sem banco)
- **Docker** (opcional, para rodar o banco via docker-compose)

---

## 🔧 Configuração

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/scheduly-api.git
cd scheduly-api
```

### 2. Configurar Banco de Dados

O projeto está configurado para usar PostgreSQL. As credenciais padrão estão em `application.yaml`:

```yaml
datasource:
  url: jdbc:postgresql://localhost:5432/banco
  username: postgres
  password: postgres
```

#### Opção A: Rodar com Docker

```bash
docker-compose up -d
```

#### Opção B: Rodar sem Banco de Dados

O projeto está configurado para iniciar sem conexão com banco de dados. As auto-configurações de DataSource e JPA estão desabilitadas no `application.yaml` por padrão.

Para habilitar o banco, descomente as configurações de `datasource` e `jpa` no arquivo `application.yaml`.

---

## 🏃 Como Executar

### Compilar o projeto

```bash
mvn clean compile
```

### Executar testes

```bash
mvn test
```

### Rodar a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

---

## 📚 Documentação da API (Swagger)

Após iniciar a aplicação, acesse a documentação interativa:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/v3/api-docs

### Endpoints Principais

| Recurso | Endpoint | Descrição |
|---------|----------|-----------|
| **Auth** | `/auth/*` | Autenticação e refresh token |
| **Clientes** | `/clients/*` | CRUD de clientes |
| **Profissionais** | `/professionals/*` | CRUD de profissionais |
| **Serviços** | `/services/*` | CRUD de serviços |
| **Agendamentos** | `/bookings/*` | Gerenciamento de agendamentos |
| **Notificações** | `/notifications/*` | Notificações do sistema |
| **CEP** | `/cep/*` | Consulta de endereço por CEP |
| **Health** | `/api/health` | Health check da API |

---

## 🔄 CI/CD

O projeto utiliza **GitHub Actions** para integração contínua. O workflow executa automaticamente:

✅ Build do projeto  
✅ Execução de testes unitários  
✅ Geração de relatórios de teste  
✅ Validação de código  

### Quando executa:
- Push para branches `main` e `develop`
- Pull requests para `main` e `develop`

Veja o arquivo `.github/workflows/ci.yml` para mais detalhes.

---

## 🔐 Segurança

- **Spring Security** configurado
- **JWT** para autenticação stateless
- **CSRF** desabilitado (API REST)
- Todas as rotas liberadas por padrão para desenvolvimento (configurar autenticação em produção)

---

## 🛠️ Geração de Código via OpenAPI

O projeto utiliza o **OpenAPI Generator** para gerar interfaces de API a partir do arquivo:

```
src/main/resources/swagger/api.yaml
```

As interfaces são geradas automaticamente durante a compilação em:

```
target/generated-sources/openapi/
```

Os controllers implementam essas interfaces, garantindo conformidade com a especificação OpenAPI.

---

## 🧪 Testes

### Executar todos os testes

```bash
mvn test
```

### Executar com cobertura

```bash
mvn test jacoco:report
```

O relatório de cobertura estará disponível em `target/site/jacoco/index.html`.

---

## 📦 Build para Produção

### Gerar JAR

```bash
mvn clean package -DskipTests
```

O arquivo JAR estará em `target/scheduly-api-0.0.1-SNAPSHOT.jar`.

### Executar o JAR

```bash
java -jar target/scheduly-api-0.0.1-SNAPSHOT.jar
```

---

## 🐳 Docker

### Build da imagem

```bash
docker build -t scheduly-api .
```

### Executar container

```bash
docker run -p 8080:8080 scheduly-api
```

---

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Add: MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

---

## 📝 Licença

Este projeto está sob a licença **MIT**.

---

## 👥 Autores

Desenvolvido com ❤️ pela equipe Scheduly.

---

## 📞 Suporte

Para dúvidas ou sugestões, abra uma [issue](https://github.com/seu-usuario/scheduly-api/issues).
