# 📅 Scheduly API  
API de agendamento de serviços para salões de beleza, manicures e profissionais autônomos.  
Desenvolvido em **Java 17** com **Spring Boot 3**.

---

## 🚀 Visão Geral

O **Scheduly** é uma plataforma de agendamento SaaS voltada para prestadores de serviços.  
A API fornece recursos para:

- Cadastro de clientes e profissionais (CRUD completo)
- Gerenciamento de serviços e preços
- Agendamento de horários
- Consulta de CEP integrada (ViaCEP)
- Autenticação e Segurança

---

## 🏗 Arquitetura (Hexagonal / Clean Architecture)

O projeto segue os princípios da **Arquitetura Limpa** (Clean Architecture), organizando o código em camadas concêntricas para isolar o domínio de detalhes técnicos.

### 🧩 Camadas

1. **🟢 Domain (`com.scheduly.api.domain`)**  
   O núcleo da aplicação. Contém as entidades (`Client`, `Professional`) e as interfaces de saída (Ports), como repositórios. Não depende de nenhum framework.

2. **🟡 Application (`com.scheduly.api.application`)**  
   Camada de orquestração. Contém os **Use Cases** (Regras de Negócio) que implementam a lógica do sistema (ex: `CreateClientUseCase`). Depende apenas do Domínio.

3. **🔴 Infrastructure (`com.scheduly.api.infrastructure`)**  
   Adaptadores de saída (Driven). Implementa as interfaces do domínio para conversar com o mundo externo (Banco de Dados, APIs de Terceiros).
   - **Persistence**: Implementação JPA/Hibernate.
   - **External**: Clientes HTTP (ex: ViaCEP).

4. **🔵 Web (`com.scheduly.api.web`)**  
   Adaptadores de entrada (Driving). Recebe as requisições HTTP e chama os casos de uso.
   - **Controllers**: Endpoints REST.
   - **Mappers**: Conversão de DTOs.

### 📂 Estrutura de Pastas Atual

```
src/main/java/com/scheduly/api/
├── config/              # Configurações do Spring (Beans, Security)
├── domain/              # Entidades e Portas (Interfaces)
│   ├── client/
│   ├── professional/
│   └── common/          # Value Objects (Address)
├── application/         # Casos de Uso (Regras de Negócio)
│   ├── client/          # UseCases de Cliente
│   ├── professional/
│   └── cep/
├── infrastructure/      # Implementações Técnicas
│   ├── persistence/     # Repositórios JPA e Entities
│   └── external/        # Integrações (ViaCEP)
└── web/                 # Camada Web (REST)
    ├── controllers/
    ├── dtos/
    └── mappers/
```

---

## 🛠 Tecnologias

- **Java 17**
- **Spring Boot 3.4.12**
- **Spring Data JPA** (PostgreSQL)
- **Spring Validation** (Bean Validation)
- **OpenAPI Generator** (Contract First Design)
- **Lombok**
- **Maven**

---

## 📋 Pré-requisitos

- **JDK 17+**
- **Maven 3.6+**

---

## 🏃 Como Executar

### 1. Compilar o projeto

```bash
mvn clean compile
```

> **Nota:** O comando acima também gera automaticamente as interfaces da API baseadas no arquivo `src/main/resources/swagger/api.yaml`.

### 2. Rodar a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

### 3. Acessar a Documentação (Swagger UI)

Explore e teste os endpoints visualmente:  
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🔄 CI/CD

O projeto possui workflows do GitHub Actions configurados para:
- Compilação automática no push
- Execução de testes unitários

---

## 🤝 Contribuindo

1. Faça um Fork
2. Crie uma Branch (`git checkout -b feature/NovaFeature`)
3. Commit suas mudanças (`git commit -m 'Add: Nova Feature'`)
4. Push para a Branch (`git push origin feature/NovaFeature`)
5. Abra um Pull Request
