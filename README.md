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

## 🏗 Arquitetura: Monolito Modular

Este projeto implementa a **Arquitetura Monolito Modular**, onde todo o código executa em um único deploy, mas está organizado em módulos independentes por domínio de negócio. Cada módulo mantém sua própria estrutura interna, comunicando-se com outros módulos através de interfaces bem definidas e eventos.

### 🎯 Princípios do Monolito Modular

- **✅ Um único deploy** - Toda aplicação executa em um único processo
- **✅ Módulos independentes** - Cada módulo representa um domínio de negócio isolado
- **✅ Baixo acoplamento** - Comunicação entre módulos via interfaces e eventos
- **✅ Alta coesão** - Cada módulo concentra toda lógica relacionada ao seu domínio
- **✅ Facilidade de evolução** - Estrutura preparada para crescimento futuro

### 📦 Módulos do Sistema

O sistema é organizado em 6 módulos principais, cada um representando um domínio de negócio:

1. **📋 Client** - Gestão de clientes
2. **👔 Professional** - Gestão de profissionais
3. **🏢 Department** - Catálogo de departamentos
4. **📅 Booking** - Sistema de agendamentos (core business)
5. **🔔 Notification** - Sistema de notificações (Email, WhatsApp)
6. **👤 User** - Autenticação e autorização

### 🏛️ Estrutura Interna de Cada Módulo

Cada módulo segue a mesma estrutura interna, organizada em camadas:

1. **🟢 Domain** - Entidades de domínio puras e interfaces de repositório (sem dependências de frameworks)
2. **🟡 Application** - Casos de uso que implementam as regras de negócio do módulo
3. **🔴 Infrastructure** - Implementações técnicas (persistência JPA, integrações externas)
4. **🔵 Web** - Controllers REST que expõem as funcionalidades via HTTP

### 📊 Diagrama de Arquitetura

```
┌─────────────────────────────────────────────────────────────────┐
│                   Scheduly API - Monolito Modular               │
│                     (1 processo, 1 deploy)                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │
│  │   Client    │  │ Professional│  │  Department │           │
│  │   Module    │  │   Module    │  │   Module    │           │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘           │
│         │                 │                 │                   │
│         └─────────────────┴─────────────────┘                   │
│                           │                                     │
│                  ┌────────▼────────┐                           │
│                  │  Booking Module │                           │
│                  │   (Core Business)│                           │
│                  └────────┬────────┘                           │
│                           │                                     │
│                  ┌────────▼────────┐                           │
│                  │Notification     │                           │
│                  │Module (Listener)│                           │
│                  └─────────────────┘                           │
│                                                                 │
│  Comunicação: Interfaces (síncrono) + Eventos (assíncrono)     │
│                                                                 │
├─────────────────────────────────────────────────────────────────┤
│                           │                                     │
│              ┌────────────▼────────────┐                       │
│              │   PostgreSQL Database    │                       │
│              └─────────────────────────┘                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### 📂 Estrutura de Pastas

```
src/main/java/com/scheduly/api/
├── config/                    # Configurações globais
│
├── domain/                    # 🟢 Domain (entidades e interfaces por módulo)
│   ├── client/                # 📦 Módulo Client
│   │   ├── Client.java
│   │   └── ClientRepository.java
│   ├── professional/          # 📦 Módulo Professional
│   ├── booking/               # 📦 Módulo Booking
│   │   └── events/            # Eventos de domínio
│   ├── department/            # 📦 Módulo Department
│   ├── notification/          # 📦 Módulo Notification
│   ├── user/                  # 📦 Módulo User
│   └── common/                # Componentes compartilhados
│
├── application/               # 🟡 Application (casos de uso por módulo)
│   ├── client/
│   ├── professional/
│   ├── booking/
│   ├── department/
│   ├── notification/
│   │   └── BookingNotificationListener.java  # Listener de eventos
│   └── auth/
│
├── infrastructure/            # 🔴 Infrastructure (implementações técnicas)
│   ├── persistence/           # Implementações de repositórios (JPA)
│   │   ├── client/
│   │   ├── professional/
│   │   ├── booking/
│   │   ├── department/
│   │   └── ...
│   ├── notifications/         # Implementações de notificação
│   └── auth/                  # Implementações de autenticação
│
└── web/                       # 🔵 Web (controllers e DTOs)
    ├── controllers/
    ├── dtos/
    └── mappers/
```

### 🔗 Comunicação Entre Módulos

A comunicação entre módulos segue dois padrões principais:

#### 1. **Interfaces (Comunicação Síncrona)**
Quando um módulo precisa consultar dados de outro módulo, usa as interfaces (repositórios) expostas:

```
CreateBookingUseCase (Módulo Booking)
    ├── usa ClientRepository → valida se cliente existe
    ├── usa ProfessionalRepository → valida se profissional existe
    └── usa DepartmentRepository → calcula duração do departamento
```

#### 2. **Eventos de Domínio (Comunicação Assíncrona)**
Para ações que podem ser processadas de forma assíncrona, os módulos publicam eventos:

```
Booking Module
    └── publica BookingCreatedEvent
            ↓
Notification Module (Listener)
    └── escuta evento → envia notificações
```

**Exemplo Real:** Quando um agendamento é criado, o módulo Booking publica um evento. O módulo Notification escuta esse evento e automaticamente envia e-mails e WhatsApp para o cliente e profissional, sem que o Booking precise conhecer o módulo de Notification.

### ✅ Benefícios do Monolito Modular

- **🚀 Simplicidade Operacional** - Um único deploy, um único banco de dados
- **📁 Organização Clara** - Código organizado por domínio de negócio, fácil de navegar
- **🧪 Testabilidade** - Cada módulo pode ser testado isoladamente
- **🔧 Manutenibilidade** - Mudanças em um módulo não afetam outros
- **⚡ Performance** - Chamadas locais, sem overhead de rede
- **📈 Escalável** - Estrutura preparada para crescimento

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
