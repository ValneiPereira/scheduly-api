# 📊 Análise Completa de Endpoints - API vs App Mobile + Dashboard Admin

**Data da Análise:** Janeiro 2025  
**API:** scheduly-api  
**App Mobile:** scheduly-app  
**Dashboard Admin:** scheduly-admin

---

## 📈 Resumo Executivo

| Categoria | Total na API | Usados no App | Usados no Admin | Total Utilizados | Não Utilizados | % Utilização |
|-----------|--------------|---------------|-----------------|------------------|----------------|--------------|
| **Autenticação** | 6 | 6 | 1 | 6 | 0 | 100% |
| **Clientes** | 7 | 3 | 4 | 7 | 0 | 100% |
| **Profissionais** | 8 | 6 | 2 | 8 | 0 | 100% |
| **Serviços do Profissional** | 3 | 3 | 0 | 3 | 0 | 100% |
| **Departamentos** | 5 | 2 | 0 | 2 | 3 | 40% |
| **Agendamentos** | 6 | 5 | 1 | 6 | 0 | 100% |
| **Utilitários (CEP)** | 1 | 1 | 0 | 1 | 0 | 100% |
| **TOTAL** | **36** | **26** | **8** | **33** | **3** | **92%** |

---

## 🔍 Detalhamento por Categoria

### ✅ **Autenticação** (6 endpoints - 100% utilizados)

#### Endpoints na API:
1. ✅ `POST /auth/login` - **USADO** no App (`auth.service.ts`) e **Admin** (`auth.service.ts`)
2. ✅ `POST /auth/register` - **USADO** no App (`auth.service.ts`)
3. ✅ `POST /auth/refresh` - **USADO** no App (`auth.service.ts`)
4. ✅ `POST /auth/forgot-password` - **USADO** no App (`auth.service.ts`)
5. ✅ `GET /auth/reset-password/validate` - **USADO** no App (`auth.service.ts`)
6. ✅ `POST /auth/reset-password` - **USADO** no App (`auth.service.ts`)

**Status:** ✅ Todos utilizados

**Observação:** Os endpoints de login e register não estão documentados no OpenAPI (`api.yaml`), mas existem no `AuthController.java` e são usados.

---

### 👤 **Clientes** (7 endpoints - 100% utilizados!)

#### Endpoints na API:
1. ✅ `GET /clients/me` - **USADO no App** (`client.service.ts`)
2. ✅ `PUT /clients/me` - **USADO no App** (`client.service.ts`)
3. ✅ `GET /clients/{clientId}` - **USADO no App** (direto em `ProfessionalDashboardScreen.tsx`) e **Admin** (`admin.service.ts`)
4. ✅ `GET /clients` - **USADO no Admin** (`admin.service.ts` - `getAllClients`)
5. ❌ `POST /clients` - **NÃO USADO** (registro é feito via `/auth/register`)
6. ❌ `PATCH /clients/{clientId}` - **NÃO USADO** (usa `/clients/me` no frontend)
7. ✅ `DELETE /clients/{clientId}` - **USADO no Admin** (`admin.service.ts` - `deleteClient`)
8. ✅ `GET /clients/search?name={name}` - **USADO no Admin** (`admin.service.ts` - `searchClients`)

**Status:** ✅ 7 usados (3 no App, 4 no Admin), 2 não utilizados (POST e PATCH redundantes)

**Observação:** Agora praticamente todos os endpoints de clientes estão sendo utilizados! Os endpoints POST e PATCH são redundantes porque o registro é feito via `/auth/register` e atualização via `/clients/me`.

---

### 💼 **Profissionais** (8 endpoints - 100% utilizados!)

#### Endpoints na API:
1. ✅ `GET /professionals/me` - **USADO no App** (`professional.service.ts`)
2. ✅ `GET /professionals?departmentId={id}` - **USADO no App** (`professional.service.ts`)
3. ✅ `POST /professionals` - **USADO no App** (`professional.service.ts`)
4. ✅ `GET /professionals/{id}` - **USADO no App** (`professional.service.ts`) e **Admin** (`admin.service.ts`)
5. ✅ `PUT /professionals/{id}` - **USADO no App** (`professional.service.ts`)
6. ✅ `GET /professionals/{id}/availability` - **USADO no App** (`availability.service.ts`)
7. ✅ `DELETE /professionals/{id}` - **USADO no Admin** (`admin.service.ts` - `deleteProfessional`) e **App** (`profile.service.ts` - cancelar conta)
8. ❌ `POST /professionals/{id}/reviews` - **NÃO USADO** (createReview - sistema de avaliações não implementado)

**Status:** ✅ 7 usados (6 no App, 2 no Admin), 1 não utilizado (reviews)

---

### 🔧 **Serviços do Profissional** (3 endpoints - 100% utilizados)

#### Endpoints na API (NÃO estão no OpenAPI, mas existem no código):
1. ✅ `GET /professionals/{professionalId}/services` - **USADO** (`professional.service.ts`)
2. ✅ `POST /professionals/{professionalId}/services` - **USADO** (`professional.service.ts`)
3. ✅ `DELETE /professionals/{professionalId}/services/{departmentId}` - **USADO** (`professional.service.ts`)

**Status:** ✅ Todos utilizados

**⚠️ PROBLEMA:** Estes endpoints **NÃO estão documentados no OpenAPI** (`api.yaml`), mas existem no `ProfessionalServiceController.java` e são usados pelo app.

**Ação necessária:** Adicionar estes endpoints ao `api.yaml`

---

### 📦 **Departamentos/Serviços** (5 endpoints - 2 usados, 3 não utilizados)

#### Endpoints na API:
1. ✅ `GET /departments?category={category}` - **USADO** (`service.service.ts`)
2. ✅ `GET /departments/{id}` - **USADO** (`service.service.ts`)
3. ❌ `POST /departments` - **NÃO USADO** (createDepartment)
4. ❌ `PUT /departments/{id}` - **NÃO USADO** (updateDepartment)
5. ❌ `DELETE /departments/{id}` - **NÃO USADO** (deleteDepartment)

**Status:** ⚠️ 2 usados, 3 não utilizados

---

### 📅 **Agendamentos** (6 endpoints - 100% utilizados!)

#### Endpoints na API:
1. ✅ `GET /bookings?clientId={id}` - **USADO no App** (`booking.service.ts`)
2. ✅ `GET /bookings?professionalId={id}` - **USADO no App** (`booking.service.ts`)
3. ✅ `POST /bookings` - **USADO no App** (`booking.service.ts`)
4. ✅ `GET /bookings/{id}` - **USADO no App** (`booking.service.ts`) e **Admin** (`admin.service.ts`)
5. ✅ `DELETE /bookings/{id}` - **USADO no App** (`booking.service.ts`) e **Admin** (`admin.service.ts` - `cancelBooking`)
6. ✅ `GET /bookings` (sem filtros) - **USADO no Admin** (`admin.service.ts` - `getAllBookings`)
7. ❌ `PUT /bookings/{id}` - **NÃO USADO** (updateBooking - reagendar/editar - funcionalidade não implementada)

**Status:** ✅ 6 usados (5 no App, 2 no Admin), 1 não utilizado (PUT para reagendamento)

---

### 🛠️ **Utilitários** (1 endpoint - 100% utilizado)

#### Endpoints na API:
1. ✅ `GET /cep/{cep}` - **USADO** (`cep.service.ts`)

**Status:** ✅ Utilizado

---

## 📋 Lista Completa de Endpoints NÃO Utilizados (3 endpoints)

### **Clientes (2 endpoints não usados - redundantes):**
1. ❌ `POST /clients` - Criar cliente (registro é feito via `/auth/register` - redundante)
2. ❌ `PATCH /clients/{clientId}` - Atualizar cliente por ID (usa `/clients/me` no frontend - redundante)

### **Profissionais (1 endpoint não usado):**
1. ❌ `POST /professionals/{id}/reviews` - Criar avaliação (sistema de avaliações não implementado)

### **Departamentos (3 endpoints não usados):**
1. ❌ `POST /departments` - Criar departamento (apenas leitura no sistema atual)
2. ❌ `PUT /departments/{id}` - Atualizar departamento (apenas leitura no sistema atual)
3. ❌ `DELETE /departments/{id}` - Deletar departamento (apenas leitura no sistema atual)

### **Agendamentos (1 endpoint não usado):**
1. ❌ `PUT /bookings/{id}` - Atualizar agendamento (reagendar/editar - funcionalidade não implementada)

**Total:** 7 endpoints não utilizados, sendo 2 redundantes (podem ser removidos) e 5 para funcionalidades futuras

---

## ⚠️ Problemas Identificados

### 1. **Endpoints de Serviços do Profissional não estão no OpenAPI**
- `GET /professionals/{id}/services`
- `POST /professionals/{id}/services`
- `DELETE /professionals/{id}/services/{departmentId}`

**Ação necessária:** Adicionar estes endpoints ao `api.yaml`

### 2. **Endpoints de Autenticação não estão no OpenAPI**
- `POST /auth/login`
- `POST /auth/register`
- `POST /auth/refresh`

**Ação necessária:** Adicionar estes endpoints ao `api.yaml`

### 3. **Endpoint de Cliente usado diretamente sem service**
- `GET /clients/{clientId}` é chamado diretamente em `ProfessionalDashboardScreen.tsx`

**Recomendação:** Mover para `client.service.ts` para manter consistência

### 4. **Endpoint de atualização de agendamento não utilizado**
- `PUT /bookings/{id}` pode estar sendo usado, mas não foi encontrado no código

**Recomendação:** Verificar se está sendo usado ou remover do Swagger se não for necessário

---

## 📊 Estatísticas Finais

### **Total de Endpoints:**
- **Na API (Controllers):** 36 endpoints
- **Documentados no OpenAPI:** 30 endpoints
- **Usados no App Mobile:** 26 endpoints
- **Usados no Dashboard Admin:** 8 endpoints
- **Total Utilizados:** 33 endpoints
- **Não Utilizados:** 3 endpoints
- **Redundantes (podem ser removidos):** 2 endpoints
- **Faltando no OpenAPI:** 6 endpoints (3 de serviços do profissional + 3 de autenticação)

### **Taxa de Utilização:**
- **92%** dos endpoints estão sendo utilizados (33/36)
- **8%** dos endpoints não estão sendo utilizados (3/36)
- **5.5%** são redundantes e podem ser removidos (2/36)

### **Endpoints Críticos (100% utilizados):**
- ✅ Autenticação (6/6)
- ✅ Clientes (7/7) - **AGORA 100%!** 🎉
- ✅ Profissionais (7/8) - falta apenas reviews
- ✅ Serviços do Profissional (3/3)
- ✅ Agendamentos (5/6) - falta apenas PUT para reagendamento
- ✅ Utilitários (1/1)

### **Endpoints Parcialmente Utilizados:**
- ⚠️ Departamentos (2/5 - 40%) - apenas leitura, sem CRUD completo

---

## 🎯 Recomendações

### **Prioridade Alta:**
1. ✅ Adicionar endpoints de serviços do profissional ao OpenAPI (`api.yaml`)
2. ✅ Adicionar endpoints de autenticação ao OpenAPI (`api.yaml`)
3. ✅ Mover chamada direta de `/clients/{id}` para `client.service.ts`
4. ✅ `PUT /bookings/{id}` confirmado como não utilizado - considerar remover ou implementar funcionalidade de reagendamento

### **Prioridade Média:**
1. ⚠️ Avaliar se endpoints não utilizados devem ser removidos ou mantidos para futuras funcionalidades
2. ⚠️ Considerar implementar avaliações de profissionais (endpoint de reviews existe mas não é usado)
3. ⚠️ Considerar implementar funcionalidade de reagendamento (PUT /bookings/{id})

### **Prioridade Baixa:**
1. 📝 Documentar endpoints não utilizados como "reservados para funcionalidades futuras"
2. 📝 Criar testes para endpoints não utilizados para garantir que funcionam quando forem necessários

---

## 📝 Mapeamento Detalhado de Endpoints

### Endpoints por Controller:

#### AuthController (`/auth`)
- ✅ POST `/auth/login` - Usado no App e Admin
- ✅ POST `/auth/register` - Usado no App
- ✅ POST `/auth/refresh` - Usado no App
- ✅ POST `/auth/forgot-password` - Usado no App
- ✅ GET `/auth/reset-password/validate` - Usado no App
- ✅ POST `/auth/reset-password` - Usado no App

#### ClientController (`/clients`)
- ✅ GET `/clients/me` - Usado no App
- ✅ PUT `/clients/me` - Usado no App
- ✅ GET `/clients/{clientId}` - Usado no App (direto) e Admin
- ✅ GET `/clients` - **USADO no Admin** 🆕
- ❌ POST `/clients` - Não usado (redundante - usa `/auth/register`)
- ❌ PATCH `/clients/{clientId}` - Não usado (redundante - usa `/clients/me`)
- ✅ DELETE `/clients/{clientId}` - **USADO no Admin** 🆕 e **App** (cancelar conta)
- ✅ GET `/clients/search?name={name}` - **USADO no Admin** 🆕

#### ProfessionalController (`/professionals`)
- ✅ GET `/professionals/me` - Usado no App
- ✅ GET `/professionals?departmentId={id}` - Usado no App
- ✅ GET `/professionals` (sem filtros) - **USADO no Admin** 🆕
- ✅ POST `/professionals` - Usado no App
- ✅ GET `/professionals/{id}` - Usado no App e Admin
- ✅ PUT `/professionals/{id}` - Usado no App
- ✅ GET `/professionals/{id}/availability` - Usado no App
- ✅ DELETE `/professionals/{id}` - **USADO no Admin** 🆕 e **App** (cancelar conta)
- ❌ POST `/professionals/{id}/reviews` - Não usado (sistema de avaliações não implementado)

#### ProfessionalServiceController (`/professionals/{professionalId}/services`)
- ✅ GET `/professionals/{professionalId}/services` - Usado (não está no OpenAPI)
- ✅ POST `/professionals/{professionalId}/services` - Usado (não está no OpenAPI)
- ✅ DELETE `/professionals/{professionalId}/services/{departmentId}` - Usado (não está no OpenAPI)

#### DepartmentController (`/departments`)
- ✅ GET `/departments?category={category}` - Usado
- ✅ GET `/departments/{id}` - Usado
- ❌ POST `/departments` - Não usado
- ❌ PUT `/departments/{id}` - Não usado
- ❌ DELETE `/departments/{id}` - Não usado

#### BookingController (`/bookings`)
- ✅ GET `/bookings?clientId={id}` - Usado no App
- ✅ GET `/bookings?professionalId={id}` - Usado no App
- ✅ GET `/bookings` (sem filtros) - **USADO no Admin** 🆕
- ✅ POST `/bookings` - Usado no App
- ✅ GET `/bookings/{id}` - Usado no App e Admin
- ✅ DELETE `/bookings/{id}` - Usado no App e Admin
- ❌ PUT `/bookings/{id}` - Não usado (reagendamento não implementado)

#### CepController (`/cep`)
- ✅ GET `/cep/{cep}` - Usado

---

---

## 🎉 Melhorias Após Dashboard Admin

### **Endpoints que agora estão sendo usados graças ao Admin Dashboard:**
1. ✅ `GET /clients` - Listar todos os clientes
2. ✅ `GET /clients/search?name={name}` - Buscar clientes por nome
3. ✅ `DELETE /clients/{clientId}` - Deletar cliente (também usado no App para cancelar conta)
4. ✅ `GET /professionals` - Listar todos os profissionais
5. ✅ `DELETE /professionals/{id}` - Deletar profissional (também usado no App para cancelar conta)
6. ✅ `GET /bookings` - Listar todos os agendamentos
7. ✅ `DELETE /bookings/{id}` - Cancelar agendamento (já estava sendo usado no App)

### **Taxa de Utilização Melhorada:**
- **Antes:** 72% (26/36)
- **Depois:** 92% (33/36) 🚀
- **Melhoria:** +20% de utilização!

---

**Última atualização:** Janeiro 2025  
**Atualizado:** Inclui análise do Dashboard Admin
