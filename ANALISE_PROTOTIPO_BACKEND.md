# 📋 Análise do Protótipo vs Backend Implementado - Scheduly API

## 📊 Resumo Executivo

Este documento apresenta uma análise comparativa entre as telas do protótipo (descritas na imagem fornecida) e o estado atual de implementação do backend, identificando o que já foi feito e o que ainda precisa ser desenvolvido.

---

## 🎯 Visão Geral das Telas do Protótipo

O fluxo do app segue esta sequência:

1. **Login** → `POST /auth/login`
2. **Home** → `GET /services` (ou `/departments`)
3. **Selecionar Profissional** → `GET /professionals?serviceId=X`
4. **Agendar Horário** → `GET /availability?professionalId=X&date=YYYY-MM-DD`
5. **Confirmar Agendamento** → `POST /bookings`
6. **Meus Agendamentos** → `GET /bookings?clientId=X`

---

## ✅ O QUE JÁ ESTÁ IMPLEMENTADO

### 1. ✅ Autenticação e Registro (EPIC 2 - 100% Concluído)

**Endpoint:** `POST /auth/login` ✅
- Implementado em `AuthController`
- Retorna JWT token
- Validação de credenciais
- **Status:** ✅ Completo

**Endpoint:** `POST /auth/register` ✅
- Implementado em `AuthController`
- Cria usuário e cliente vinculado
- **Status:** ✅ Completo

**O que falta:**
- [ ] Recuperação de senha (mencionado na tela mas não implementado)

---

### 2. ✅ Gestão de Serviços/Departamentos (EPIC 5 - 100% Concluído)

**Endpoint:** `GET /departments` ✅
- Implementado em `DepartmentController`
- Suporta filtro por categoria (`?category=...`)
- Retorna lista de serviços (departments)
- **Status:** ✅ Completo

**Mapeamento Frontend → Backend:**
- Frontend chama: `serviceService.list()` → `/services`
- Backend expõe: `/departments`
- **Ação necessária:** Verificar se há endpoint `/services` ou ajustar frontend

---

### 3. ✅ Gestão de Profissionais (EPIC 4 - 95% Concluído)

**Endpoint:** `GET /professionals` ✅
- Implementado em `ProfessionalController`
- Suporta filtro por `departmentId` (`?departmentId=X`)
- Retorna lista de profissionais com rating
- **Status:** ✅ Completo

**Endpoint:** `GET /professionals/{id}` ✅
- Implementado
- Retorna detalhes completos do profissional
- **Status:** ✅ Completo

**Sistema de Avaliações:**
- ✅ Entidade `ProfessionalReview` criada
- ✅ Endpoint `POST /professionals/{id}/reviews` para criar avaliações
- ✅ Cálculo de rating médio implementado
- **Status:** ✅ Completo

**O que falta:**
- [ ] Endpoint específico `/professionals?serviceId=X` (atualmente usa `departmentId`)

---

### 4. ✅ Sistema de Agendamentos (EPIC 6 - 100% Concluído)

**Endpoint:** `POST /api/bookings` ✅
- Implementado em `BookingController`
- Valida disponibilidade antes de criar
- Valida horário de trabalho do profissional
- Valida conflitos de horário
- Dispara eventos para notificações
- **Status:** ✅ Completo

**Endpoint:** `GET /api/bookings` ✅
- Implementado em `BookingController`
- Suporta filtros: `clientId`, `professionalId`, `date`
- Retorna lista de agendamentos
- **Status:** ✅ Completo

**Endpoint:** `GET /api/bookings/{id}` ✅
- Implementado
- Retorna detalhes do agendamento
- **Status:** ✅ Completo

**Endpoint:** `DELETE /api/bookings/{id}` ✅
- Implementado (cancelamento)
- **Status:** ✅ Completo

---

### 5. ✅ Sistema de Notificações (EPIC 7 - 100% Concluído)

**Funcionalidades:**
- ✅ Notificações automáticas ao criar agendamento
- ✅ Notificações por email implementadas
- ✅ Templates de email (Thymeleaf)
- ✅ Listener de eventos de domínio
- ✅ Sistema de scheduler para lembretes

**O que falta:**
- [ ] Integração real com WhatsApp Business API (atualmente mock/log)

---

## ❌ O QUE FALTA IMPLEMENTAR

### 1. ❌ Endpoint de Disponibilidade (Crítico)

**Tela 4 do Protótipo:** "Agendar Horário"
**Endpoint esperado:** `GET /availability?professionalId=X&date=YYYY-MM-DD`

**O que precisa ser feito:**
```java
@GetMapping("/availability")
public ResponseEntity<AvailabilityResponse> getAvailability(
    @RequestParam Long professionalId,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
) {
    // Retornar slots disponíveis para o profissional na data especificada
    // Considerar:
    // - Horários de trabalho do profissional
    // - Dias de trabalho (workingDays)
    // - Agendamentos já existentes
    // - Duração dos serviços
    // - Intervalos entre serviços
}
```

**Resposta esperada:**
```json
{
  "professionalId": 1,
  "date": "2024-04-25",
  "availableSlots": [
    "09:00",
    "09:30",
    "10:00",
    "10:30",
    "14:00",
    "14:30",
    "15:00"
  ]
}
```

**Prioridade:** 🔴 CRÍTICA - Necessário para a tela de agendamento funcionar corretamente.

---

### 2. ❌ Endpoint de Serviços (Nome alternativo)

**Problema identificado:**
- Frontend usa `/services`
- Backend usa `/departments`

**Soluções possíveis:**
1. Criar alias: `@GetMapping("/services")` que chama `listDepartments()`
2. Ajustar frontend para usar `/departments`
3. Criar endpoint dedicado `/services` se houver diferença conceitual

**Prioridade:** 🟠 ALTA - Necessário para o frontend funcionar.

---

### 3. ❌ Filtro de Profissionais por Serviço

**Problema:**
- Frontend passa `serviceId` para buscar profissionais
- Backend espera `departmentId`

**Solução:**
- Se `serviceId == departmentId`, apenas ajustar documentação
- Se forem diferentes, criar mapeamento ou endpoint específico

**Prioridade:** 🟠 ALTA

---

### 4. ⚠️ Integração WhatsApp (Pendente)

**Estado atual:** Mock/Log apenas
**Necessário:** Integração real com WhatsApp Business API

**Prioridade:** 🟡 MÉDIA (funcionalidade já existe com email)

---

### 5. ❌ Recuperação de Senha

**Tela de Login:** Tem link "Esqueceu a senha?"
**Backend:** Não implementado

**O que precisa:**
```java
@PostMapping("/auth/forgot-password")
public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
    // Gerar token de redefinição
    // Enviar email com link
}

@PostMapping("/auth/reset-password")
public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
    // Validar token
    // Atualizar senha
}
```

**Prioridade:** 🟡 MÉDIA

---

## 🔍 COMPARAÇÃO DETALHADA: TELAS vs BACKEND

### Tela 1: Login
| Requisito | Status | Observação |
|-----------|--------|------------|
| `POST /auth/login` | ✅ Implementado | Funcional |
| Campos: email, senha | ✅ Validado | Bean Validation |
| Retorno: token JWT | ✅ Implementado | JwtProvider |
| Link "Esqueceu a senha?" | ❌ Não implementado | Faltando |

---

### Tela 2: Home
| Requisito | Status | Observação |
|-----------|--------|------------|
| `GET /services` | ⚠️ Parcial | Backend usa `/departments` |
| Listar categorias | ✅ Implementado | Filtro `?category=...` |
| Saudação com nome | ✅ Implementado | Via token JWT |
| Navegação Agenda/Perfil | ✅ Endpoints existem | `/bookings`, perfil via user |

---

### Tela 3: Selecionar Profissional
| Requisito | Status | Observação |
|-----------|--------|------------|
| `GET /professionals?serviceId=X` | ⚠️ Parcial | Usa `departmentId` |
| Rating (estrelas) | ✅ Implementado | Campo `rating` na resposta |
| Número de avaliações | ❓ Verificar | Pode estar no response |
| Especialização | ✅ Implementado | Via `specialtyIds` |

---

### Tela 4: Agendar Horário
| Requisito | Status | Observação |
|-----------|--------|------------|
| `GET /availability` | ❌ Não implementado | **CRÍTICO** |
| Seleção de data | ⚠️ Parcial | Frontend gera, backend não valida |
| Seleção de horário | ❌ Falta endpoint | Não há consulta de slots |
| Validação de disponibilidade | ✅ No POST | Só no momento da criação |

**Problema identificado:**
- Frontend mostra horários fixos: `['09:00', '10:00', ...]`
- Deveria consultar backend para ver horários disponíveis reais
- Backend precisa retornar slots disponíveis baseado em:
  - Horário de trabalho do profissional
  - Agendamentos existentes
  - Duração do serviço

---

### Tela 5: Confirmar Agendamento
| Requisito | Status | Observação |
|-----------|--------|------------|
| `POST /bookings` | ✅ Implementado | Funcional |
| Resumo (serviço, profissional, data, hora) | ✅ Validação completa | Todos os dados validados |
| Validação final de disponibilidade | ✅ Implementado | Antes de criar |
| Notificação WhatsApp | ⚠️ Mock apenas | Email funciona |

---

### Tela 6: Meus Agendamentos
| Requisito | Status | Observação |
|-----------|--------|------------|
| `GET /bookings?clientId=X` | ✅ Implementado | Funcional |
| Lista de agendamentos | ✅ Implementado | Com filtros |
| Status (Confirmado, Pendente, etc) | ✅ Implementado | Enum `BookingStatus` |
| Detalhes (serviço, profissional, data, hora) | ✅ Implementado | Response completo |
| Cancelamento | ✅ Implementado | `DELETE /bookings/{id}` |

---

## 🎯 PRIORIZAÇÃO DE IMPLEMENTAÇÃO

### 🔴 CRÍTICO (Bloqueador para MVP)
1. **Endpoint de Disponibilidade** (`GET /availability`)
   - Sem isso, o usuário não pode escolher horários corretamente
   - Impacto: Alto - Bloqueia fluxo principal

2. **Alias `/services` ou ajuste frontend**
   - Frontend não consegue listar serviços
   - Impacto: Alto - Bloqueia tela inicial

3. **Filtro de profissionais por `serviceId`**
   - Frontend não consegue buscar profissionais por serviço
   - Impacto: Alto - Bloqueia seleção de profissional

### 🟠 ALTA (Importante mas não bloqueador)
4. **Número de avaliações no response de profissionais**
   - Melhora UX na seleção

5. **Documentação de endpoints**
   - Facilita integração frontend

### 🟡 MÉDIA (Pode esperar)
6. **Recuperação de senha**
   - Funcionalidade comum, mas não bloqueia MVP

7. **Integração WhatsApp real**
   - Email já funciona, WhatsApp é complementar

---

## 📝 RECOMENDAÇÕES TÉCNICAS

### 1. Endpoint de Disponibilidade

**Implementação sugerida:**

```java
@GetMapping("/availability")
public ResponseEntity<AvailabilityResponse> getAvailability(
    @RequestParam Long professionalId,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
) {
    // 1. Buscar profissional
    // 2. Verificar dia da semana (workingDays)
    // 3. Obter horário de trabalho (workStartTime, workEndTime)
    // 4. Buscar agendamentos existentes para a data
    // 5. Calcular slots disponíveis (considerando duração do serviço)
    // 6. Retornar slots livres
}
```

**Considerações:**
- Intervalos de 30 minutos (padrão)
- Considerar duração do serviço/departamento
- Verificar se é dia de trabalho do profissional
- Desconsiderar horários já agendados
- Adicionar margem entre serviços (ex: 15min)

### 2. Padronização de Endpoints

**Opção 1: Criar alias**
```java
@GetMapping({"/services", "/departments"})
public ResponseEntity<List<DepartmentResponse>> listServices(...) {
    // Mesma implementação
}
```

**Opção 2: Ajustar frontend**
- Atualizar `serviceService.list()` para usar `/departments`

**Recomendação:** Opção 1 (menos impacto no frontend)

### 3. Response de Profissionais

**Adicionar ao `ProfessionalResponse`:**
```java
private Double rating; // ✅ Já existe
private Integer reviewCount; // ❌ Falta adicionar
private String specialization; // ❌ Falta (ou derivar de specialtyIds)
```

---

## 🧪 TESTES RECOMENDADOS

### Testes de Integração Necessários

1. **Teste de Disponibilidade**
   - Profissional sem agendamentos → retorna todos os slots
   - Profissional com agendamento → exclui horário ocupado
   - Dia fora dos workingDays → retorna vazio
   - Horário fora do workStartTime/workEndTime → excluído

2. **Teste de Conflitos**
   - Tentar agendar horário já ocupado → deve falhar
   - Dois usuários tentando mesmo horário → apenas um sucesso

3. **Teste de Fluxo Completo**
   - Login → Listar serviços → Selecionar profissional → Ver disponibilidade → Agendar

---

## 📊 ESTIMATIVA DE ESFORÇO

| Tarefa | Complexidade | Estimativa |
|--------|--------------|------------|
| Endpoint de Disponibilidade | Média | 8h |
| Alias `/services` | Baixa | 1h |
| Ajuste filtro profissionais | Baixa | 2h |
| Adicionar reviewCount | Baixa | 2h |
| Recuperação de senha | Média | 6h |
| **Total Crítico** | - | **11h** |
| **Total Completo** | - | **19h** |

---

## ✅ CONCLUSÃO

O backend está **muito bem estruturado** e a maioria das funcionalidades críticas já está implementada. Os principais gaps são:

1. **Endpoint de disponibilidade** (crítico)
2. **Compatibilidade de nomes de endpoints** (crítico)
3. **Melhorias de UX** (avaliações, especialização)

Com essas implementações, o sistema estará **100% funcional** para o MVP conforme o protótipo.

---

## 🔗 PRÓXIMOS PASSOS

1. ✅ Implementar `GET /availability`
2. ✅ Criar alias `/services` → `/departments`
3. ✅ Adicionar `reviewCount` ao `ProfessionalResponse`
4. ✅ Documentar endpoints no Swagger
5. ⏳ Implementar recuperação de senha (futuro)
6. ⏳ Integração WhatsApp real (futuro)

---

**Documento criado em:** $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
**Versão:** 1.0
