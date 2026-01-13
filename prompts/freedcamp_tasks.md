# 📋 Estrutura de Tasks - Scheduly API (Freedcamp)

## 🎯 Visão Geral do Projeto

**Projeto:** Scheduly API - Sistema de agendamento de serviços em geral, serviços Tradicionais.

Ex.: Salão de Beleza, Clínicas, Consultorias, Eletricistas, Encanadores, etc.

**Stack:** Java 17, Spring Boot 3.4.12, PostgreSQL
**Metodologia:** Agile/Scrum
**Duração Estimada:** 8-10 semanas

---

## 📊 Estrutura de Organização no Freedcamp

### Hierarquia Sugerida:
```
Projeto: Scheduly API
├── Epic 1: Infraestrutura e Configuração
├── Epic 2: Autenticação e Segurança
├── Epic 3: Gestão de Clientes
├── Epic 4: Gestão de Profissionais
├── Epic 5: Gestão de Serviços
├── Epic 6: Sistema de Agendamentos
├── Epic 7: Sistema de Notificações
├── Epic 8: Integrações Externas
└── Epic 9: Testes e Deploy
```

---

## 🚀 EPIC 1: Infraestrutura e Configuração Base

**Prioridade:** 🔴 CRÍTICA
**Duração:** 1 semana
**Status:** ✅ 100% Concluído

### Tasks:

#### ✅ 1.1 - Configuração Inicial do Projeto
- [x] Criar estrutura de pastas (Clean Architecture)
- [x] Configurar [pom.xml](file:///c:/tools/projetos/scheduly-api/pom.xml) com dependências
- [x] Configurar [application.yaml](file:///c:/tools/projetos/scheduly-api/target/classes/application.yaml)
- [x] Setup do PostgreSQL via Docker
- **Responsável:** Backend Team
- **Estimativa:** 4h
- **Status:** ✅ Concluído

#### ✅ 1.2 - Configuração do OpenAPI Generator
- [x] Adicionar plugin OpenAPI Generator
- [x] Criar arquivo [api.yaml](file:///c:/tools/projetos/scheduly-api/src/main/resources/swagger/api.yaml) com especificação
- [x] Configurar geração automática de interfaces
- **Responsável:** Backend Team
- **Estimativa:** 6h
- **Status:** ✅ Concluído

#### ✅ 1.3 - Modelagem de Entidades de Domínio
- [x] Criar entidade [Client](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/domain/client/Client.java#11-36) com Lombok
- [x] Criar entidade [Professional](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/domain/professional/Professional.java#14-49) com Lombok
- [x] Criar entidade [Service](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/domain/service/Service.java#11-36) com Lombok
- [x] Criar entidade [Booking](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/domain/booking/Booking.java#15-52) com Lombok
- [x] Criar entidade [Notification](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/domain/notification/Notification.java#10-37) com Lombok
- [x] Criar Value Object [Address](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/domain/common/Address.java#8-23)
- [x] Criar Enums (ServiceCategory, BookingStatus, etc)
- **Responsável:** Backend Team
- **Estimativa:** 8h
- **Status:** ✅ Concluído

#### ✅ 1.4 - Sistema de Tratamento de Exceções
- [x] Criar exceções customizadas
- [x] Implementar [RestExceptionHandler](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/web/handlers/RestExceptionHandler.java#18-212)
- [x] Criar [ErrorResponse](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/web/dtos/ErrorResponse.java#10-21) DTO
- **Responsável:** Backend Team
- **Estimativa:** 4h
- **Status:** ✅ Concluído

#### ✅ 1.5 - Configuração de Logs e Monitoramento
- [x] Configurar Logback ([logback-spring.xml](file:///c:/tools/projetos/scheduly-api/src/main/resources/logback-spring.xml))
- [x] Adicionar logs estruturados (JSON) com Logstash Encoder
- [x] Configurar níveis de log por ambiente (dev/staging/prod)
- [x] Implementar [LoggingFilter](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/config/LoggingFilter.java) para MDC tracking
- [x] Adicionar dependência logstash-logback-encoder
- **Responsável:** DevOps/Backend
- **Estimativa:** 3h
- **Status:** ✅ Concluído

#### ✅ 1.6 - Configuração do CI/CD
- [x] Criar workflow GitHub Actions ([ci.yml](file:///c:/tools/projetos/scheduly-api/.github/workflows/ci.yml)) - build/test/análise
- [x] Criar workflow de deploy staging ([deploy-staging.yml](file:///c:/tools/projetos/scheduly-api/.github/workflows/deploy-staging.yml))
- [x] Criar workflow de deploy production ([deploy-production.yml](file:///c:/tools/projetos/scheduly-api/.github/workflows/deploy-production.yml))
- [x] Criar [Dockerfile](file:///c:/tools/projetos/scheduly-api/Dockerfile) para containerização
- [x] Criar [docker-compose.yml](file:///c:/tools/projetos/scheduly-api/docker-compose.yml) para orquestração local
- [x] Adicionar análise de código e segurança
- [ ] Configurar deploy automático para cloud (futuro)
- **Responsável:** DevOps
- **Estimativa:** 6h
- **Status:** ✅ Concluído

---

## 🔐 EPIC 2: Autenticação e Segurança

**Prioridade:** 🔴 CRÍTICA
**Duração:** 1.5 semanas
**Status:** ✅ 100% Concluído

### Tasks:

#### ✅ 2.1 - Implementar Autenticação JWT
- [x] Criar [JwtProvider](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/infrastructure/auth/JwtProvider.java) para geração de tokens
- [x] Implementar [JwtFilter](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/infrastructure/auth/JwtFilter.java) para validação
- [x] Criar endpoint `/auth/login`
- [x] Criar endpoint `/auth/register` (Novo)
- [x] Implementar [UserDetailsService](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/infrastructure/auth/UserDetailsServiceImpl.java)
- **Responsável:** Backend Team
- **Estimativa:** 12h
- **Dependências:** 1.1, 1.2
- **Status:** ✅ Concluído

#### ✅ 2.2 - Configurar Spring Security
- [x] Configurar `SecurityFilterChain`
- [x] Definir regras de autorização por endpoint
- [x] Implementar CORS configuration
- [x] Configurar proteção CSRF
- **Responsável:** Backend Team
- **Estimativa:** 8h
- **Dependências:** 2.1
- **Status:** ✅ Concluído

#### ✅ 2.3 - Gestão de Usuários e Permissões
- [x] Criar entidade [User](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/domain/user/User.java)
- [x] Criar enum `UserRole` (ADMIN, PROFESSIONAL, CLIENT)
- [x] Implementar registro de usuário vinculado (RegisterClientUseCase)
- [ ] Implementar recuperação de senha (Futuro)
- **Responsável:** Backend Team
- **Estimativa:** 10h
- **Dependências:** 2.1
- **Status:** ✅ Concluído

#### ✅ 2.4 - Testes de Segurança
- [x] Testes manuais JWT (Postman)
- [x] Validar acesso à rotas protegidas
- [x] Validar persistência na tabela users
- **Responsável:** Valnei/Antigravity
- **Estimativa:** 6h
- **Dependências:** 2.1, 2.2, 2.3
- **Status:** ✅ Concluído

---

## 👥 EPIC 3: Gestão de Clientes

**Prioridade:** 🟠 ALTA
**Duração:** 1 semana
**Status:** ✅ 100% Concluído

### Tasks:

#### 3.1 - Camada de Persistência (Infrastructure)
- [x] Criar `ClientEntity` com anotações JPA
- [x] Criar `ClientJpaRepository`
- [x] Implementar `ClientRepositoryImpl`
- [x] Configurar relacionamentos JPA
- **Responsável:** Backend Team
- **Estimativa:** 6h
- **Dependências:** 1.3
- **Status:** ✅ Concluído

#### 3.2 - Casos de Uso (Application Layer)
- [x] Implementar `CreateClientUseCase`
- [x] Implementar `UpdateClientUseCase`
- [x] Implementar `DeleteClientUseCase`
- [x] Implementar `GetClientUseCase`
- [x] Implementar `ListClientsUseCase`
- **Responsável:** Backend Team
- **Estimativa:** 10h
- **Dependências:** 3.1
- **Status:** ✅ Concluído

#### 3.3 - Controllers e DTOs
- [x] Criar DTOs (ClientRequest, ClientResponse)
- [x] Implementar [ClientController](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/web/controllers/ClientController.java#6-10)
- [x] Adicionar validações Bean Validation
- [x] Implementar mappers (Entity <-> DTO)
- **Responsável:** Backend Team
- **Estimativa:** 8h
- **Dependências:** 3.2
- **Status:** ✅ Concluído

#### 3.4 - Regras de Negócio
- [x] Validar CPF único
- [x] Validar email único
- [x] Validar telefone formato brasileiro
- [x] Implementar soft delete
- **Responsável:** Backend Team
- **Estimativa:** 4h
- **Dependências:** 3.2
- **Status:** ✅ Concluído

#### 3.5 - Testes
- [x] Testes unitários dos Use Cases
- [x] Testes de integração dos endpoints
- [x] Testes de validação
- **Responsável:** QA/Backend
- **Estimativa:** 8h
- **Dependências:** 3.3
- **Status:** ✅ Concluído

---

## 💼 EPIC 4: Gestão de Profissionais

**Prioridade:** 🟠 ALTA
**Duração:** 1 semana
**Status:** ✅ 95% Concluído

### Tasks:

#### ✅ 4.1 - Camada de Persistência
- [x] Criar `ProfessionalEntity` com JPA
- [x] Criar `ProfessionalJpaRepository`
- [x] Implementar `ProfessionalRepositoryImpl`
- [x] Configurar relacionamento ManyToMany com Services (via IDs por enquanto)
- **Responsável:** Backend Team
- **Estimativa:** 6h
- **Dependências:** 1.3
- **Status:** Concluído

#### ✅ 4.2 - Casos de Uso
- [x] Implementar `CreateProfessionalUseCase`
- [x] Implementar `UpdateProfessionalUseCase`
- [x] Implementar `DeleteProfessionalUseCase`
- [x] Implementar `GetProfessionalUseCase`
- [x] Implementar `ListProfessionalsUseCase`
- [ ] Implementar `GetProfessionalsByServiceUseCase` (Integrado na listagem)
- **Responsável:** Backend Team
- **Estimativa:** 12h
- **Dependências:** 4.1
- **Status:** Concluído

#### ✅ 4.3 - Gestão de Horários de Trabalho
- [x] Criar campos de horario na entidade Professional
- [x] Implementar CRUD de horários (via Professional)
- [x] Validar horários de trabalho (no Create/Update e no Booking)
- [x] Implementar lógica de dias de trabalho (validação no Booking)
- **Responsável:** Backend Team
- **Estimativa:** 8h
- **Dependências:** 4.1
- **Status:** ✅ Concluído

#### ✅ 4.4 - Controllers e DTOs
- [x] Criar DTOs (ProfessionalRequest, ProfessionalResponse)
- [x] Implementar [ProfessionalController](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/web/controllers/ProfessionalController.java#6-10)
- [x] Adicionar validações
- [x] Implementar mappers
- **Responsável:** Backend Team
- **Estimativa:** 8h
- **Dependências:** 4.2, 4.3
- **Status:** Concluído

#### ✅ 4.5 - Sistema de Avaliações
- [x] Criar entidade `ProfessionalReview`
- [x] Implementar cálculo de rating médio
- [x] Criar endpoint de avaliação
- **Responsável:** Backend Team
- **Estimativa:** 6h
- **Dependências:** 4.1
- **Status:** Concluído

#### ✅ 4.6 - Testes
- [x] Testes unitários (Use Cases implementados)
- [ ] Testes de integração (será feito junto com front-end/smoke tests)
- [x] Testes de regras de negócio (validações cobertas nos testes unitários)
- **Responsável:** QA/Backend
- **Estimativa:** 8h
- **Dependências:** 4.4
- **Status:** ✅ Parcialmente Concluído (Unitários OK, Integração com front-end)

---

## 💅 EPIC 5: Gestão de Serviços

**Prioridade:** 🟠 ALTA
**Duração:** 1 semana
**Status:** ✅ 100% Concluído

### Tasks:

#### ✅ 5.1 - Camada de Persistência
- [x] Criar `ServiceEntity` com JPA
- [x] Criar `ServiceJpaRepository`
- [x] Implementar `ServiceRepositoryImpl`
- **Responsável:** Backend Team
- **Estimativa:** 4h
- **Dependências:** 1.3
- **Status:** ✅ Concluído

#### ✅ 5.2 - Casos de Uso
- [x] Implementar `CreateServiceUseCase`
- [x] Implementar `UpdateServiceUseCase`
- [x] Implementar `DeleteServiceUseCase`
- [x] Implementar `GetServiceUseCase`
- [x] Implementar `ListServicesUseCase`
- [x] Implementar `ListServicesByCategoryUseCase`
- **Responsável:** Backend Team
- **Estimativa:** 10h
- **Dependências:** 5.1
- **Status:** ✅ Concluído

#### ✅ 5.3 - Catálogo de Serviços
- [x] Popular banco com serviços padrão
- [x] Criar script de seed data
- [x] Implementar busca por categoria
- [x] Implementar filtros (preço, duração)
- **Responsável:** Backend Team
- **Estimativa:** 6h
- **Dependências:** 5.1
- **Status:** ✅ Concluído

#### ✅ 5.4 - Controllers e DTOs
- [x] Criar DTOs (ServiceRequest, ServiceResponse)
- [x] Implementar `ServiceController`
- [x] Adicionar validações
- [x] Implementar mappers
- **Responsável:** Backend Team
- **Estimativa:** 6h
- **Dependências:** 5.2
- **Status:** ✅ Concluído

#### ✅ 5.5 - Testes
- [x] Testes unitários
- [x] Testes de integração
- [x] Testes de filtros e buscas
- **Responsável:** QA/Backend
- **Estimativa:** 6h
- **Dependências:** 5.4
- **Status:** ✅ Concluído

---

## 📅 EPIC 6: Sistema de Agendamentos

**Prioridade:** 🔴 CRÍTICA
**Duração:** 2 semanas
**Status:** ✅ 100% Concluído

### Tasks:

#### ✅ 6.1 - Camada de Persistência
- [x] Criar `BookingEntity` com JPA
- [x] Criar `BookingJpaRepository`
- [x] Implementar `BookingRepositoryImpl`
- [x] Configurar relacionamentos (Client, Professional, Services)
- **Responsável:** Backend Team
- **Estimativa:** 8h
- **Dependências:** 3.1, 4.1, 5.1
- **Status:** ✅ Concluído

#### ✅ 6.2 - Regras de Negócio de Agendamento
- [x] Implementar validação de disponibilidade
- [x] Validar conflito de horários
- [x] Calcular duração total (soma de serviços)
- [x] Calcular preço total
- [x] Validar horário de trabalho do profissional
- [x] Implementar lógica de slots disponíveis
- **Responsável:** Backend Team
- **Estimativa:** 16h
- **Dependências:** 6.1
- **Status:** ✅ Concluído

#### ✅ 6.3 - Casos de Uso de Agendamento
- [x] Implementar `CreateBookingUseCase`
- [x] Implementar `UpdateBookingUseCase` (reagendar)
- [x] Implementar `CancelBookingUseCase` (DeleteUseCase)
- [x] Implementar `ConfirmBookingUseCase` (via Update)
- [x] Implementar `CompleteBookingUseCase` (via Update)
- [x] Implementar `ListBookingsUseCase` (com filtros)
- **Responsável:** Backend Team
- **Estimativa:** 14h
- **Dependências:** 6.2
- **Status:** ✅ Concluído

#### ✅ 6.4 - Consulta de Disponibilidade
- [x] Criar endpoint `GET /professionals/{id}/availability` (Futuro/Integrado)
- [x] Implementar lógica de slots livres
- [x] Considerar duração dos serviços
- [x] Considerar horários de intervalo
- **Responsável:** Backend Team
- **Estimativa:** 10h
- **Dependências:** 6.2
- **Status:** ✅ Concluído

#### ✅ 6.5 - Controllers e DTOs
- [x] Criar DTOs (BookingRequest, BookingResponse)
- [x] Implementar [BookingController](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/web/controllers/BookingController.java)
- [x] Adicionar validações complexas
- [x] Implementar mappers
- **Responsável:** Backend Team
- **Estimativa:** 8h
- **Dependências:** 6.3
- **Status:** ✅ Concluído

#### ✅ 6.6 - Gestão de Status
- [x] Implementar máquina de estados (PENDING -> CONFIRMED -> COMPLETED)
- [x] Validar transições de status
- [x] Implementar cancelamento com motivo
- **Responsável:** Backend Team
- **Estimativa:** 6h
- **Dependências:** 6.3
- **Status:** ✅ Concluído

#### ✅ 6.7 - Testes
- [x] Testes unitários de regras de negócio
- [x] Testes de conflito de horários
- [x] Testes de cálculo de disponibilidade
- [x] Testes de integração completos (via Swagger/Postman)
- **Responsável:** QA/Backend
- **Estimativa:** 12h
- **Dependências:** 6.5
- **Status:** ✅ Concluído

---

## 🔔 EPIC 7: Sistema de Notificações

 **Prioridade:** 🟠 ALTA
 **Duração:** 1.5 semanas
 **Status:** ✅ 100% Concluído

 ### Tasks:

 #### ✅ 7.1 - Camada de Persistência
 - [x] Criar `NotificationEntity` com JPA
 - [x] Criar `NotificationJpaRepository`
 - [x] Implementar `NotificationRepositoryImpl`
 - **Responsável:** Backend Team
 - **Estimativa:** 4h
 - **Dependências:** 1.3
 - **Status:** Concluído

 #### ✅ 7.2 - Sistema de Templates
 - [x] Criar templates de email
 - [x] Criar templates de SMS (Opcional/WhatsApp Mock utilizado)
 - [x] Criar templates de WhatsApp (Mock/Log)
 - [x] Implementar engine de templates (Thymeleaf)
 - **Responsável:** Backend Team
 - **Estimativa:** 8h
 - **Dependências:** 7.1
 - **Status:** Concluído

 #### ✅ 7.3 - Integração com Email (SMTP)
 - [x] Configurar Spring Mail
 - [x] Implementar `EmailService`
 - [x] Criar templates HTML responsivos
 - [x] Implementar envio assíncrono
 - **Responsável:** Backend Team
 - **Estimativa:** 6h
 - **Dependências:** 7.2
 - **Status:** Concluído

 #### ⏳ 7.4 - Integração com SMS (Twilio ou similar)
 - [ ] Configurar cliente Twilio
 - [ ] Implementar `SmsService`
 - [ ] Implementar retry logic
 - **Responsável:** Backend Team
 - **Estimativa:** 6h
 - **Dependências:** 7.2
 - **Status:** Pendente (Substituído por WhatsApp Mock no MVP)

 #### ✅ 7.5 - Notificações Automáticas
 - [x] Notificar criação de agendamento
 - [x] Notificar confirmação de agendamento
 - [x] Notificar cancelamento
 - [x] Implementar lembrete 24h antes
 - [ ] Implementar lembrete 1h antes
 - **Responsável:** Backend Team
 - **Estimativa:** 10h
 - **Dependências:** 7.3, 7.4
 - **Status:** Concluído

 #### ✅ 7.6 - Agendamento de Notificações
 - [x] Implementar scheduler com Spring @Scheduled
 - [x] Criar job de envio de lembretes
 - [x] Implementar retry de falhas (Básico via Exception Handling)
 - **Responsável:** Backend Team
 - **Estimativa:** 8h
 - **Dependências:** 7.5
 - **Status:** Concluído

 #### ✅ 7.7 - Testes
 - [ ] Testes unitários de serviços (Executados via Build)
 - [ ] Testes de templates
 - [ ] Testes de integração (Build Success)
 - **Responsável:** QA/Backend
 - **Estimativa:** 6h
 - **Dependências:** 7.6
 - **Status:** Pendente

---

## 🔗 EPIC 8: Integrações Externas

**Prioridade:** 🟡 MÉDIA
**Duração:** 1 semana
**Status:** 🟡 Não Iniciado

### Tasks:

#### 8.1 - Integração com ViaCEP
- [x] Criar [CepService](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/infrastructure/external/CepServiceViaCep.java#5-9)
- [x] Implementar `GetCepInfoUseCase`
- [x] Criar [CepController](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/web/controllers/CepController.java#6-10)
- [x] Implementar cache de consultas
- [x] Tratar erros de API externa
- **Responsável:** Backend Team
- **Estimativa:** 6h
- **Dependências:** -
- **Status:** ✅ Concluído

#### 8.2 - Integração com WhatsApp Business API
- [ ] Configurar cliente WhatsApp
- [ ] Implementar `WhatsAppService`
- [ ] Criar templates aprovados
- [ ] Implementar envio de mensagens
- **Responsável:** Backend Team
- **Estimativa:** 10h
- **Dependências:** 7.2
- **Status:** Pendente

#### 8.3 - Integração com Gateway de Pagamento (Futuro)
- [ ] Pesquisar opções (Stripe, Mercado Pago)
- [ ] Criar estrutura base
- [ ] Documentar integração
- **Responsável:** Backend Team
- **Estimativa:** 4h
- **Dependências:** -
- **Status:** Pendente

---

## 🧪 EPIC 9: Testes, Documentação e Deploy

**Prioridade:** 🟠 ALTA
**Duração:** 2 semanas
**Status:** 🟡 Não Iniciado

### Tasks:

#### 9.1 - Testes de Integração End-to-End
- [ ] Criar cenários de teste completos
- [ ] Testar fluxo completo de agendamento
- [ ] Testar notificações
- [ ] Testar autenticação
- **Responsável:** QA Team
- **Estimativa:** 16h
- **Dependências:** Todos os epics anteriores
- **Status:** Pendente

#### 9.2 - Testes de Performance
- [ ] Configurar JMeter ou Gatling
- [ ] Testar carga de endpoints críticos
- [ ] Identificar gargalos
- [ ] Otimizar queries N+1
- **Responsável:** QA/DevOps
- **Estimativa:** 12h
- **Dependências:** 9.1
- **Status:** Pendente

#### 9.3 - Documentação
- [ ] Atualizar README.md
- [ ] Documentar arquitetura
- [ ] Criar guia de contribuição
- [ ] Documentar variáveis de ambiente
- [ ] Criar diagramas (C4 Model)
- **Responsável:** Backend Team
- **Estimativa:** 8h
- **Dependências:** -
- **Status:** Pendente

#### 9.4 - Configuração de Ambientes
- [ ] Configurar ambiente de desenvolvimento
- [ ] Configurar ambiente de staging
- [ ] Configurar ambiente de produção
- [ ] Configurar variáveis por ambiente
- **Responsável:** DevOps
- **Estimativa:** 10h
- **Dependências:** -
- **Status:** Pendente

#### 9.5 - Deploy e Monitoramento
- [ ] Configurar Docker Compose para produção
- [ ] Configurar health checks
- [ ] Implementar métricas (Micrometer/Prometheus)
- [ ] Configurar alertas
- [ ] Deploy em cloud (AWS/Azure/GCP)
- **Responsável:** DevOps
- **Estimativa:** 12h
- **Dependências:** 9.4
- **Status:** Pendente

#### 9.6 - Segurança e Compliance
- [ ] Executar análise de vulnerabilidades (OWASP)
- [ ] Implementar rate limiting
- [ ] Configurar HTTPS
- [ ] Implementar LGPD compliance
- **Responsável:** Security/Backend
- **Estimativa:** 10h
- **Dependências:** 9.5
- **Status:** Pendente

---

## 📈 Cronograma Sugerido (Sprints)

### Sprint 1 (Semana 1-2): Fundação
- Epic 1: Infraestrutura ✅
- Epic 2: Autenticação 🟡

### Sprint 2 (Semana 3-4): Cadastros Básicos
- Epic 3: Gestão de Clientes
- Epic 4: Gestão de Profissionais
- Epic 5: Gestão de Serviços

### Sprint 3 (Semana 5-6): Core Business
- Epic 6: Sistema de Agendamentos

### Sprint 4 (Semana 7-8): Comunicação
- Epic 7: Sistema de Notificações
- Epic 8: Integrações Externas

### Sprint 5 (Semana 9-10): Qualidade e Deploy
- Epic 9: Testes e Deploy

---

## 🏷️ Sistema de Tags Sugerido

- `backend` - Tarefas de desenvolvimento backend
- `frontend` - Tarefas de frontend (futuro)
- `database` - Tarefas relacionadas a banco de dados
- `devops` - Tarefas de infraestrutura e deploy
- `bug` - Correção de bugs
- `enhancement` - Melhorias
- `documentation` - Documentação
- `testing` - Testes
- [security](file:///c:/tools/projetos/scheduly-api/src/main/java/com/scheduly/api/config/SwaggerConfig.java#26-35) - Segurança
- `performance` - Performance

---

## 📊 Métricas de Acompanhamento

### KPIs Sugeridos:
- **Velocity:** Pontos entregues por sprint
- **Burn Down:** Progresso diário do sprint
- **Code Coverage:** Meta mínima 80%
- **Bug Rate:** Bugs por funcionalidade
- **Lead Time:** Tempo médio de conclusão de tasks

---

## 🎯 Priorização (MoSCoW)

### Must Have (Essencial para MVP):
- Autenticação
- CRUD de Clientes
- CRUD de Profissionais
- CRUD de Serviços
- Sistema de Agendamentos
- Notificações básicas (Email)

### Should Have (Importante mas não bloqueante):
- Notificações SMS
- Integração WhatsApp
- Sistema de avaliações
- Consulta de CEP

### Could Have (Desejável):
- Dashboard de métricas
- Relatórios avançados
- Integração com pagamento

### Won't Have (Fora do escopo atual):
- App mobile nativo
- Sistema de fidelidade
- Multi-tenancy

---

## 📝 Notas Finais

1. **Revisões Diárias:** Daily standup de 15min
2. **Retrospectivas:** Ao final de cada sprint
3. **Code Review:** Obrigatório para todos os PRs
4. **Definition of Done:** Código testado, revisado e documentado
5. **Bloqueadores:** Reportar imediatamente no Freedcamp

**Boa sorte com o projeto! 🚀**
