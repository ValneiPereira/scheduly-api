# 📋 Plano de Ação - Implementação de Funcionalidades Críticas

**Data de Criação:** 2025-01-XX  
**Versão:** 1.2  
**Status Geral:** 🟢 Fases 1 e 2 Concluídas | 🟡 Fase 3 Pendente

---

## 📦 Projeto: Scheduly API

**Sistema de Agendamento de Serviços em Geral - Serviços Tradicionais**

O Scheduly é uma plataforma de agendamento SaaS para diversos tipos de prestadores de serviços, incluindo:

- **Salões de Beleza** (Cabeleireiro, Barbeiro, Esteticista, Designer de Sobrancelha, Manicure)
- **Clínicas** (Psicólogo, Fisioterapeuta, Nutricionista, Personal Trainer)
- **Serviços Técnicos** (Eletricista, Encanador, Técnico em Informática, Montador de Móveis, Pedreiro)
- **Educação** (Aulas Particulares, Professor de Idiomas, Reforço Escolar, Mentor)
- **Outros** serviços personalizados

**Não é apenas para manicure** - É um sistema completo e flexível para agendamento de serviços tradicionais diversos.

---

## 🎯 Objetivo

Implementar funcionalidades críticas identificadas na análise do protótipo vs backend, garantindo que o sistema esteja 100% funcional para o MVP e suporte todos os tipos de serviços definidos nos enums `DepartmentCategory` e `DepartmentSubcategory`.

---

## 📌 Resumo Executivo

Este plano detalha a implementação de 5 funcionalidades principais:

1. **🔴 CRÍTICO:** Endpoint de Disponibilidade - Permite consultar horários disponíveis de profissionais
2. **🔴 CRÍTICO:** Verificar Compatibilidade serviceId/departmentId - Garantir que frontend e backend estão alinhados
3. **🟠 ALTA:** Adicionar reviewCount - Melhorar compatibilidade com frontend
4. **🟠 ALTA:** Adicionar specialization - Facilitar exibição no frontend
5. **🟡 MÉDIA:** Recuperação de Senha - Funcionalidade complementar

**Tempo Total Estimado:** ~18 horas  
**Prioridade:** Implementar Fase 1 (Críticas) primeiro, depois Fase 2 e 3

---

## 📊 Visão Geral das Tarefas

| Prioridade | Tarefa | Complexidade | Estimativa | Status |
|------------|--------|--------------|------------|--------|
| 🔴 CRÍTICA | Endpoint de Disponibilidade | Alta | 8h | ✅ Concluída |
| 🔴 CRÍTICA | Verificar Compatibilidade serviceId/departmentId | Baixa | 0.5h | ✅ Concluída |
| 🟠 ALTA | Adicionar reviewCount (alias totalReviews) | Baixa | 1h | ✅ Concluída |
| 🟠 ALTA | Adicionar specialization ao ProfessionalResponse | Média | 2h | ✅ Concluída |
| 🟡 MÉDIA | Recuperação de Senha | Média | 6h | ⏳ Pendente |

**Tempo Total Estimado:** ~17.5 horas  
**Tempo Restante:** ~6 horas

---

## 📌 Tipos de Serviços Disponíveis

### Categorias de Serviços (DepartmentCategory)

O sistema suporta as seguintes categorias principais:

1. **BELEZA** - Serviços de beleza e estética
2. **SAUDE** - Serviços de saúde e bem-estar
3. **SERVICOS** - Serviços técnicos e manutenção
4. **EDUCACAO** - Serviços educacionais e ensino
5. **OUTROS** - Serviços diversos

### Subcategorias de Serviços (DepartmentSubcategory)

**BELEZA:**
- Cabeleireiro
- Barbeiro
- Esteticista
- Designer de Sobrancelha
- Manicure

**SAUDE:**
- Psicólogo
- Fisioterapeuta
- Nutricionista
- Personal Trainer

**SERVICOS:**
- Eletricista
- Encanador
- Técnico em Informática
- Montador de Móveis
- Pedreiro

**EDUCACAO:**
- Aula Particular
- Professor de Idiomas
- Reforço Escolar
- Mentor

**OUTROS:**
- Outros (genérico para serviços não categorizados)

---

## 📌 Nota sobre Endpoint de Criação de Serviços

**✅ Endpoint já existe:** `POST /api/departments`

O sistema já possui endpoint completo para criação de serviços/departments:

```http
POST /api/departments
Content-Type: application/json

{
  "name": "Manicure Básica",
  "description": "Serviço completo de manicure",
  "category": "BELEZA",
  "subcategory": "MANICURE",
  "price": 30.00,
  "duration": 60,
  ...
}
```

**Análise da Arquitetura:**

✅ **Faz sentido ter o endpoint de criação** porque:
1. Os enums definem os **tipos** de serviços (subcategorias)
2. Mas cada profissional pode criar **instâncias específicas** de serviços dentro desses tipos
3. Exemplo: Dentro de "MANICURE", pode ter "Manicure Básica", "Francesinha", "Manicure Spa", cada uma com preço e duração diferentes

**Exemplo Prático:**
- Tipo: `MANICURE` (enum)
- Instâncias:
  - "Manicure Básica" - R$ 30, 60min
  - "Francesinha" - R$ 35, 45min  
  - "Manicure Spa" - R$ 50, 90min

**Conclusão:** O endpoint de criação é necessário e já está implementado. O enum garante padronização dos tipos, enquanto o endpoint permite criar variações específicas de cada tipo.

---

## 🔴 FASE 1: Funcionalidades Críticas (Prioridade Máxima)

### 1.1. Implementar Endpoint de Disponibilidade

**Objetivo:** Criar endpoint `GET /availability` para retornar slots de horários disponíveis para um profissional em uma data específica.

#### 📝 Detalhamento Técnico

**Endpoint:**
```
GET /api/professionals/{professionalId}/availability?date=YYYY-MM-DD&durationMinutes=60
```

**Request:**
- `professionalId` (path parameter) - ID do profissional
- `date` (query parameter, obrigatório) - Data no formato YYYY-MM-DD
- `durationMinutes` (query parameter, opcional) - Duração em minutos (padrão: 30)

**Response:**
```json
{
  "professionalId": 1,
  "date": "2024-04-25",
  "durationMinutes": 30,
  "availableSlots": [
    "09:00",
    "09:30",
    "10:00",
    "10:30",
    "14:00",
    "14:30",
    "15:00",
    "15:30",
    "16:00"
  ]
}
```

#### ✅ Requisitos Funcionais

1. **Intervalos de 30 minutos (padrão)**
   - Gerar slots de 30 em 30 minutos
   - Permitir customização via `durationMinutes`

2. **Considerar duração do serviço/departamento**
   - Se `durationMinutes` for fornecido, considerar na geração dos slots
   - Validar se o slot cabe no horário de trabalho

3. **Verificar se é dia de trabalho do profissional**
   - Validar se a data está nos `workingDays` do profissional
   - Retornar array vazio se não for dia de trabalho

4. **Desconsiderar horários já agendados**
   - Buscar todos os agendamentos confirmados/pendentes do profissional na data
   - Excluir slots que conflitem com agendamentos existentes
   - Considerar a duração dos agendamentos existentes

5. **Respeitar horário de trabalho**
   - Não gerar slots antes de `workStartTime`
   - Não gerar slots após `workEndTime`
   - Não gerar slots que ultrapassem o horário de trabalho

#### 🏗️ Estrutura de Implementação

**1. Criar DTOs:**

```java
// AvailabilityRequest.java
public record AvailabilityRequest(
    @NotNull Long professionalId,
    @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
    @Min(15) @Max(480) Integer durationMinutes // 15min a 8h
) {
    public Integer getDurationMinutesOrDefault() {
        return durationMinutes != null ? durationMinutes : 30;
    }
}

// AvailabilityResponse.java
public record AvailabilityResponse(
    Long professionalId,
    LocalDate date,
    Integer durationMinutes,
    List<String> availableSlots // Format: "HH:mm"
) {}
```

**2. Criar Use Case:**

```java
@RequiredArgsConstructor
@Component
public class GetProfessionalAvailabilityUseCase {
    private final ProfessionalRepository professionalRepository;
    private final BookingRepository bookingRepository;
    
    public AvailabilityResponse execute(AvailabilityRequest request) {
        // 1. Buscar profissional
        // 2. Validar dia de trabalho
        // 3. Buscar agendamentos do dia
        // 4. Gerar slots disponíveis
        // 5. Filtrar slots ocupados
        // 6. Retornar resposta
    }
}
```

**3. Criar Controller:**

```java
@GetMapping("/api/professionals/{professionalId}/availability")
public ResponseEntity<AvailabilityResponse> getAvailability(
    @PathVariable Long professionalId,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
    @RequestParam(required = false) @Min(15) @Max(480) Integer durationMinutes
) {
    AvailabilityRequest request = new AvailabilityRequest(professionalId, date, durationMinutes);
    AvailabilityResponse response = getAvailabilityUseCase.execute(request);
    return ResponseEntity.ok(response);
}
```

#### 📋 Checklist de Implementação

- [x] Criar DTOs (`AvailabilityRequest`, `AvailabilityResponse`) ✅
- [x] Criar Use Case `GetProfessionalAvailabilityUseCase` ✅
- [x] Implementar lógica de geração de slots (intervalos de 30min) ✅
- [x] Implementar validação de dia de trabalho ✅
- [x] Implementar busca de agendamentos existentes ✅
- [x] Implementar filtro de slots conflitantes ✅
- [x] Implementar validação de horário de trabalho ✅
- [x] Adicionar endpoint no controller ✅
- [x] Adicionar documentação no Swagger (`api.yaml`) ✅
- [ ] Criar testes unitários (próximo passo)
- [ ] Criar testes de integração (próximo passo)

#### 🧪 Casos de Teste

1. **Profissional sem agendamentos** → Retorna todos os slots do horário de trabalho
2. **Profissional com agendamento** → Exclui horários ocupados
3. **Dia fora dos workingDays** → Retorna array vazio
4. **Horário antes de workStartTime** → Não gera slots
5. **Horário após workEndTime** → Não gera slots
6. **Slot que ultrapassa workEndTime** → Não inclui o slot
7. **Duração customizada** → Gera slots respeitando a duração
8. **Agendamento com duração longa** → Exclui múltiplos slots

#### ⏱️ Estimativa: 8 horas | ✅ Realizado: ~2h

**Breakdown:**
- DTOs e Estrutura: 1h ✅
- Use Case - Lógica Principal: 3h ✅
- Validações e Filtros: 2h ✅
- Testes: 1.5h ⏳ (pendente)
- Documentação: 0.5h ✅

**Status:** ✅ **IMPLEMENTADO** - Endpoint funcional, compilando sem erros.  
**Arquivos Criados:**
- `AvailabilityRequest.java`
- `AvailabilityResponse.java`
- `GetProfessionalAvailabilityUseCase.java`

**Arquivos Modificados:**
- `ProfessionalController.java` (adicionado endpoint)
- `api.yaml` (documentação Swagger)

---

### 1.2. Verificar Compatibilidade serviceId / departmentId

**Objetivo:** Verificar que o frontend está usando corretamente os endpoints e parâmetros, já que há uma compatibilidade semântica entre `serviceId` (frontend) e `departmentId` (backend interno).

#### 📝 Análise Atual

**Backend:**
- ✅ Aceita `serviceId` no `BookingRequest` (mantido por compatibilidade)
- ✅ Internamente converte `serviceId` → `departmentId` via `BookingMapper`
- ✅ Usa `departmentId` em `/professionals?departmentId=X`
- ✅ Endpoints usam `/departments` (não `/services`)

**Frontend:**
- ✅ `service.service.ts` já usa `/departments` ✓
- ✅ `professional.service.ts` já usa `departmentId` ✓
- ✅ `BookingScreen.tsx` usa `serviceId` como parâmetro de rota (OK - compatível)
- ✅ Internamente converte `serviceId` → `deptId` quando busca profissionais ✓

**Conclusão:** O sistema já está funcionando corretamente! O `serviceId` no frontend é mapeado para `departmentId` no backend via mapper.

#### 🔍 Pontos a Verificar

1. **Testar Fluxo Completo**
   - Home → Selecionar Serviço → Agendar
   - Verificar se tudo funciona end-to-end

2. **Documentação**
   - Atualizar comentários explicando a compatibilidade
   - Documentar no Swagger que `serviceId` = `departmentId`

#### 📋 Checklist de Verificação

- [x] Backend aceita `serviceId` e converte para `departmentId` ✓
- [x] Frontend usa `/departments` corretamente ✓
- [x] Frontend converte `serviceId` → `deptId` quando necessário ✓
- [ ] Testar fluxo completo: Home → Selecionar Serviço → Agendar (próximo passo)
- [x] Verificar se há algum lugar que precise de ajuste ✅ (não precisa)
- [x] Atualizar documentação/comentários explicando a compatibilidade ✅

#### ⏱️ Estimativa: 0.5 hora | ✅ Realizado: ~0.5h

**Status:** ✅ **CONCLUÍDA** - Sistema já funcionando corretamente, documentação atualizada no Swagger.

**Ações Realizadas:**
- ✅ Verificação completa do código
- ✅ Documentação atualizada no `api.yaml` explicando compatibilidade
- ✅ Confirmado que não são necessários ajustes de código

---

## 🟠 FASE 2: Melhorias de UX (Prioridade Alta)

### 2.1. Adicionar reviewCount ao ProfessionalResponse

**Objetivo:** Adicionar campo `reviewCount` (alias de `totalReviews`) para compatibilidade com frontend.

#### 📝 Análise Atual

**Backend:**
- ✅ Já tem `totalReviews` em `ProfessionalResponse`
- ✅ Frontend já usa `totalReviews` corretamente

**Solução:** Frontend já está usando `totalReviews` que já existe no backend. Não foi necessário adicionar `reviewCount` como alias.

#### 📋 Checklist

- [x] Verificar se frontend realmente precisa de `reviewCount` ✅ (não precisa)
- [x] Frontend já usa `totalReviews` ✅
- [x] Confirmado que não são necessárias alterações ✅

#### ⏱️ Estimativa: 1 hora | ✅ Realizado: ~0.2h (apenas verificação)

**Status:** ✅ **CONCLUÍDA** - Frontend já usa `totalReviews` corretamente, sem necessidade de ajustes.

---

### 2.2. Adicionar specialization ao ProfessionalResponse

**Objetivo:** Adicionar campo `specialization` derivado de `specialtyIds` para facilitar exibição no frontend.

#### 📝 Detalhamento

**Problema:** Frontend precisa mostrar a especialização do profissional, mas atualmente só tem `specialtyIds` (array de IDs).

**Solução Implementada:** Adicionar campo `specialization` calculado no backend baseado no primeiro `specialtyId`, buscando o nome do departamento correspondente e formatando como "Especialista em [nome do serviço]".

#### 📋 Implementação Realizada

**1. Adicionado campo `specialization` ao `ProfessionalResponse`:**
```java
public record ProfessionalResponse(
    // ... campos existentes
    List<Long> specialtyIds,
    BigDecimal rating,
    Integer totalReviews,
    String specialization, // ✅ NOVO CAMPO
    // ... outros campos
) {}
```

**2. Atualizado `ProfissionalMapper` para calcular specialization:**
```java
private String calculateSpecialization(List<Long> specialtyIds) {
    if (specialtyIds == null || specialtyIds.isEmpty()) {
        return null;
    }
    // Busca o primeiro specialtyId (primeira especialização)
    Long firstSpecialtyId = specialtyIds.get(0);
    return departmentRepository.findById(firstSpecialtyId)
            .map(department -> "Especialista em " + department.getName())
            .orElse(null);
}
```

**3. Atualizado Swagger (`api.yaml`):**
- Adicionado campo `specialization` no schema `ProfessionalResponse`
- Campo nullable: true
- Exemplo: "Especialista em Manicure Basica"

**4. Atualizado Frontend:**
- Adicionado campo `specialization?: string` no tipo `ProfessionalResponse`

#### 📋 Checklist

- [x] Decidir estratégia de implementação ✅ (buscar primeiro specialtyId)
- [x] Adicionar campo `specialization` no `ProfessionalResponse` ✅
- [x] Atualizar mapper para calcular/formatar specialization ✅
- [x] Atualizar Swagger (`api.yaml`) ✅
- [x] Compilar e verificar sem erros ✅
- [x] Atualizar frontend (tipo TypeScript) ✅
- [x] Verificar protótipo HTML (já estava correto) ✅

#### ⏱️ Estimativa: 2 horas | ✅ Realizado: ~1.5h

**Breakdown:**
- Análise e decisão: 0.2h ✅
- Implementação: 1h ✅
- Atualização frontend e documentação: 0.3h ✅

**Status:** ✅ **IMPLEMENTADO** - Campo `specialization` adicionado ao `ProfessionalResponse`, calculado automaticamente no mapper baseado no primeiro `specialtyId`.

**Arquivos Modificados:**
- `ProfessionalResponse.java` (adicionado campo `specialization`)
- `ProfissionalMapper.java` (adicionado método `calculateSpecialization` e dependência `DepartmentRepository`)
- `api.yaml` (adicionado campo no schema)
- `api.ts` (frontend - adicionado campo no tipo)

---

## 🟡 FASE 3: Funcionalidades Complementares (Prioridade Média)

### 3.1. Implementar Recuperação de Senha

**Objetivo:** Implementar fluxo completo de recuperação de senha (esqueci minha senha).

#### 📝 Fluxo

1. **Solicitar Recuperação:** `POST /auth/forgot-password`
   - Input: email
   - Gera token de recuperação
   - Envia email com link

2. **Validar Token:** `GET /auth/reset-password/validate?token=XXX`
   - Valida se token é válido
   - Retorna status

3. **Redefinir Senha:** `POST /auth/reset-password`
   - Input: token, novaSenha
   - Valida token
   - Atualiza senha
   - Invalida token

#### 📋 Estrutura de Implementação

**1. Criar Entidade/Value Object para Token:**
```java
// PasswordResetToken.java
@Data
@Builder
public class PasswordResetToken {
    private Long userId;
    private String token;
    private LocalDateTime expiresAt;
    private Boolean used;
}
```

**2. Criar DTOs:**
```java
// ForgotPasswordRequest.java
public record ForgotPasswordRequest(@Email @NotBlank String email) {}

// ResetPasswordRequest.java
public record ResetPasswordRequest(
    @NotBlank String token,
    @NotBlank @Size(min = 8) String newPassword
) {}
```

**3. Criar Use Cases:**
- `ForgotPasswordUseCase` - Gera token e envia email
- `ResetPasswordUseCase` - Valida token e atualiza senha

**4. Integração com Email:**
- Usar `EmailService` existente
- Criar template de email para recuperação

#### 📋 Checklist

- [ ] Criar entidade/value object para tokens
- [ ] Criar tabela no banco (ou usar tabela existente)
- [ ] Criar DTOs
- [ ] Criar Use Case `ForgotPasswordUseCase`
- [ ] Criar Use Case `ResetPasswordUseCase`
- [ ] Criar endpoints no `AuthController`
- [ ] Criar template de email
- [ ] Implementar geração de token seguro
- [ ] Implementar expiração de token (ex: 1h)
- [ ] Adicionar documentação no Swagger
- [ ] Criar testes unitários
- [ ] Criar testes de integração

#### ⏱️ Estimativa: 6 horas

**Breakdown:**
- Estrutura e DTOs: 1h
- Use Cases: 2h
- Integração Email: 1.5h
- Testes: 1h
- Documentação: 0.5h

---

## 📊 Cronograma Sugerido

### Semana 1: Funcionalidades Críticas ✅ CONCLUÍDA
- ✅ **Dia 1-2:** Endpoint de Disponibilidade (8h) - **REALIZADO em ~2h**
- ✅ **Dia 3:** Verificação Compatibilidade (0.5h) - **REALIZADO**

### Semana 2: Melhorias e Complementos
- **Dia 1:** ReviewCount e Specialization (3h)
- **Dia 2-3:** Recuperação de Senha (6h)

---

## ✅ Critérios de Aceitação

### Endpoint de Disponibilidade
- [ ] Endpoint retorna slots disponíveis corretamente
- [ ] Respeita horário de trabalho do profissional
- [ ] Valida dia de trabalho
- [ ] Exclui horários agendados
- [ ] Considera duração do serviço
- [ ] Testes passam (cobertura > 80%)

### Ajustes Frontend
- [ ] Frontend funciona corretamente com `departmentId`
- [ ] Fluxo completo de agendamento funciona
- [ ] Sem erros de console

### Melhorias UX
- [x] `reviewCount` ou `totalReviews` disponível ✅
- [x] `specialization` disponível no response ✅
- [x] Frontend pode exibir informações corretamente ✅

### Recuperação de Senha
- [ ] Email é enviado corretamente
- [ ] Token expira após 1h
- [ ] Senha é atualizada com sucesso
- [ ] Token é invalidado após uso

---

## 🚀 Próximos Passos

1. ✅ **Aprovar plano de ação** ✓
2. ✅ **Branch criada:** `feature/fase-um`
3. ✅ **Implementar Fase 1** (Funcionalidades Críticas) - **CONCLUÍDA**
4. ✅ **Implementar Fase 2** (Melhorias de UX) - **CONCLUÍDA**
5. **Testar endpoints** via Swagger UI ou testes automatizados
6. **Code Review**
7. **Merge para develop**
8. **Implementar Fase 3** (Recuperação de Senha)

---

## ✅ Status de Implementação - FASES 1 e 2

### Resumo da Implementação - FASE 1:

**✅ Endpoint de Disponibilidade:**
- Endpoint: `GET /api/professionals/{professionalId}/availability`
- Funcionalidades implementadas:
  - ✅ Intervalos de 30 minutos (padrão, customizável)
  - ✅ Considera duração do serviço
  - ✅ Valida dia de trabalho
  - ✅ Exclui horários agendados
  - ✅ Respeita horário de trabalho
- **Compilação:** ✅ Sem erros
- **Status:** Pronto para testes

**✅ Compatibilidade serviceId/departmentId:**
- ✅ Verificação completa realizada
- ✅ Documentação atualizada
- ✅ Sistema funcionando corretamente
- **Status:** Documentado e validado

---

### Resumo da Implementação - FASE 2:

**✅ ReviewCount/TotalReviews:**
- ✅ Verificado que frontend já usa `totalReviews` corretamente
- ✅ Não foi necessário adicionar alias `reviewCount`
- **Status:** Validado - sem necessidade de alterações

**✅ Specialization:**
- ✅ Campo `specialization` adicionado ao `ProfessionalResponse`
- ✅ Mapper atualizado para calcular specialization automaticamente
- ✅ Busca o primeiro `specialtyId` e formata como "Especialista em [nome do serviço]"
- ✅ Swagger atualizado com novo campo
- ✅ Frontend atualizado (tipo TypeScript)
- ✅ Protótipo HTML já estava correto
- **Compilação:** ✅ Sem erros
- **Status:** Implementado e pronto para uso

### Exemplo de Uso do Endpoint:

```bash
GET /api/professionals/1/availability?date=2024-04-25&durationMinutes=30
```

**Resposta:**
```json
{
  "professionalId": 1,
  "date": "2024-04-25",
  "durationMinutes": 30,
  "availableSlots": [
    "09:00",
    "09:30",
    "10:00",
    "14:00",
    "14:30"
  ]
}
```

---

## 📝 Notas Adicionais

### Considerações Técnicas

1. **Endpoint de Disponibilidade:**
   - Considerar cache para melhor performance
   - Implementar rate limiting
   - Logs detalhados para debug

2. **Performance:**
   - Queries otimizadas para buscar agendamentos
   - Índices no banco se necessário

3. **Segurança:**
   - Tokens de recuperação com expiração curta
   - Rate limiting no forgot-password

---

**Documento criado em:** 2025-01-XX  
**Última atualização:** 2025-01-XX  
**Responsável:** Backend Team
