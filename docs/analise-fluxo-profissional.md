# Análise do Fluxo do Profissional - Comparação com Protótipo

## Fluxo Esperado (baseado no protótipo)

1. **Login** → `POST /auth/login`
2. **Seleção tipo profissional ou cliente** → Escolha no app
3. **Profissional sem endereço** → Cadastro de endereço (confirma endereço) → **NÃO vai para dashboard**
4. **Categoria de serviço** → Escolhe o tipo de serviço
5. **Cadastro mostra nome e valor** → Para cadastrar serviço
6. **Horários disponível** → Seleção de horários
7. **Confirmação/ "Tá Marcado!"** → Mostra cadastro confirmado com botão para dashboard

---

## Endpoints Disponíveis na API

### ✅ 1. Login
- **Endpoint**: `POST /auth/login`
- **Status**: ✅ Implementado
- **Request**: `{ email, password }`
- **Response**: `{ accessToken, refreshToken, role }`

### ⚠️ 2. Seleção tipo profissional ou cliente
- **Status**: ⚠️ Não há endpoint específico - é escolha no app
- **Observação**: O app precisa decidir se é cliente ou profissional antes de chamar os endpoints

### ⚠️ 3. Cadastro de Profissional e Endereço
- **Endpoint**: `POST /professionals`
- **Status**: ✅ Implementado, mas endereço é opcional no cadastro
- **Request**: `ProfessionalRequest` (inclui `address` opcional)
- **Problema**: Se profissional não tem endereço, precisa cadastrar depois
- **Solução necessária**: 
  - Endpoint para atualizar endereço do profissional: `PUT /professionals/me` (já existe!)
  - Ou endpoint específico: `PUT /professionals/{id}/address`

### ✅ 4. Categoria de Serviço
- **Endpoint**: `GET /departments`
- **Status**: ✅ Implementado
- **Query params**: `?category=BELEZA` (opcional)
- **Response**: Lista de departamentos com categorias

### ✅ 5. Cadastro de Serviço (nome e valor)
- **Endpoint**: `POST /professionals/{professionalId}/services`
- **Status**: ✅ Implementado
- **Request**: 
  ```json
  {
    "departmentId": 1,
    "priceCents": 6000
  }
  ```
- **Observação**: `durationMinutes` é opcional (usa padrão do Department)

### ✅ 6. Horários Disponíveis
- **Endpoint**: `GET /professionals/{professionalId}/availability`
- **Status**: ✅ Implementado
- **Query params**: `?date=2024-04-25&durationMinutes=45`
- **Response**: Lista de horários disponíveis no formato `HH:mm`

### ✅ 7. Confirmação de Agendamento
- **Endpoint**: `POST /bookings`
- **Status**: ✅ Implementado
- **Request**:
  ```json
  {
    "clientId": 1,
    "professionalId": 1,
    "serviceId": 1,
    "startAt": "2024-04-25T14:00:00"
  }
  ```
- **Response**: `BookingResponse` com `serviceName`, `serviceCategory`, `priceCents`

---

## Problemas Identificados

### 🔴 Problema 1: Fluxo de Cadastro de Endereço
**Situação**: Profissional pode ser cadastrado sem endereço, mas o fluxo exige que ele cadastre antes de ir para o dashboard.

**Solução Atual**:
- `PUT /professionals/me` permite atualizar o perfil incluindo endereço
- Mas não há validação que impeça acesso ao dashboard sem endereço

**Solução Recomendada**:
1. Adicionar validação no `GET /professionals/me` para verificar se tem endereço
2. Ou criar endpoint específico: `PUT /professionals/me/address`

### 🟡 Problema 2: Registro de Profissional
**Situação**: Não há endpoint específico de registro de profissional (como há para cliente: `POST /auth/register`).

**Solução Atual**: 
- Usar `POST /professionals` diretamente
- Mas não cria usuário no sistema de autenticação

**Solução Recomendada**:
- Criar `POST /auth/register-professional` similar ao `POST /auth/register` para clientes

### ✅ Problema 3: Resposta de Confirmação
**Status**: ✅ RESOLVIDO
- `BookingResponse` agora inclui `serviceName`, `serviceCategory`, `priceCents`
- Tela de confirmação pode mostrar todas as informações

---

## Recomendações

### Prioridade Alta
1. **Criar endpoint de registro de profissional** (`POST /auth/register-professional`)
2. **Adicionar validação de endereço** no fluxo do profissional
3. **Endpoint específico para atualizar endereço** (`PUT /professionals/me/address`)

### Prioridade Média
1. **Endpoint para verificar se profissional está completo** (`GET /professionals/me/status`)
   - Retorna se tem endereço, serviços cadastrados, etc.

### Prioridade Baixa
1. **Melhorar mensagens de erro** para guiar o profissional no fluxo

---

## Fluxo Atual vs Protótipo

| Etapa | Protótipo | API Atual | Status |
|-------|-----------|-----------|--------|
| Login | ✅ | ✅ `POST /auth/login` | ✅ OK |
| Seleção tipo | ✅ | ⚠️ No app | ⚠️ OK |
| Cadastro endereço | ✅ Obrigatório | ⚠️ Opcional | 🔴 Ajustar |
| Categoria serviço | ✅ | ✅ `GET /departments` | ✅ OK |
| Cadastro serviço | ✅ Nome + Valor | ✅ `POST /professionals/{id}/services` | ✅ OK |
| Horários | ✅ | ✅ `GET /professionals/{id}/availability` | ✅ OK |
| Confirmação | ✅ "Tá Marcado!" | ✅ `POST /bookings` | ✅ OK |

---

## Próximos Passos

1. Implementar validação de endereço obrigatório para profissional
2. Criar endpoint de registro de profissional com autenticação
3. Adicionar endpoint para atualizar apenas endereço
4. Testar fluxo completo no app
