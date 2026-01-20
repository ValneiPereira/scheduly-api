# 🚫 Endpoints NÃO Utilizados

## Resumo Rápido

**Total de endpoints não utilizados: 3**  
**Total de endpoints redundantes (podem ser removidos): 2**

**Taxa de utilização:** 92% (33/36 endpoints)

---

## 📋 Lista Completa de Endpoints NÃO Utilizados

### 👤 **Clientes** (2 endpoints não usados - REDUNDANTES)

1. ❌ `POST /clients` - Criar cliente
   - **Motivo:** Redundante - o registro é feito via `/auth/register`
   - **Recomendação:** Pode ser removido ou mantido apenas para uso administrativo futuro

2. ❌ `PATCH /clients/{clientId}` - Atualizar cliente por ID
   - **Motivo:** Redundante - o app usa `/clients/me` para atualização
   - **Recomendação:** Pode ser removido ou mantido apenas para uso administrativo futuro

**✅ Agora sendo usados pelo Dashboard Admin:**
- ✅ `GET /clients` - Listar todos os clientes
- ✅ `GET /clients/search?name={name}` - Buscar cliente por nome
- ✅ `DELETE /clients/{clientId}` - Deletar cliente (também usado no App para cancelar conta)

---

### 💼 **Profissionais** (1 endpoint não usado)

1. ❌ `POST /professionals/{id}/reviews` - Criar avaliação
   - **Motivo:** Sistema de avaliações/reviews não implementado
   - **Recomendação:** Manter para futura implementação

**✅ Agora sendo usados pelo Dashboard Admin:**
- ✅ `GET /professionals` (sem filtros) - Listar todos os profissionais
- ✅ `DELETE /professionals/{id}` - Deletar profissional (também usado no App para cancelar conta)

---

### 📦 **Departamentos** (3 endpoints não usados)

1. ❌ `POST /departments` - Criar departamento
2. ❌ `PUT /departments/{id}` - Atualizar departamento
3. ❌ `DELETE /departments/{id}` - Deletar departamento

**Motivo:** O sistema atual funciona apenas como leitura de departamentos. Não há necessidade de criar/editar/deletar departamentos no fluxo atual.

**Recomendação:** Manter para funcionalidades administrativas futuras ou remover se nunca serão necessários.

---

### 📅 **Agendamentos** (1 endpoint não usado)

1. ❌ `PUT /bookings/{id}` - Atualizar agendamento (reagendar/editar)
   - **Motivo:** Funcionalidade de reagendamento não implementada
   - **Recomendação:** Manter para futura implementação (funcionalidade comum em sistemas de agendamento)

**✅ Agora sendo usado pelo Dashboard Admin:**
- ✅ `GET /bookings` (sem filtros) - Listar todos os agendamentos

---

## ⚠️ Observações Importantes

### Endpoints que existem mas NÃO estão no OpenAPI:

1. ⚠️ `GET /professionals/{id}/services` - **USADO** mas não documentado
2. ⚠️ `POST /professionals/{id}/services` - **USADO** mas não documentado
3. ⚠️ `DELETE /professionals/{id}/services/{departmentId}` - **USADO** mas não documentado
4. ⚠️ `POST /auth/login` - **USADO** (App e Admin) mas não documentado
5. ⚠️ `POST /auth/register` - **USADO** mas não documentado
6. ⚠️ `POST /auth/refresh` - **USADO** mas não documentado

**Ação necessária:** Adicionar estes endpoints ao `api.yaml`

---

## 💡 Recomendações

### Endpoints que PODEM ser removidos (redundantes):
1. ❌ `POST /clients` - Redundante (registro via `/auth/register`)
2. ❌ `PATCH /clients/{clientId}` - Redundante (atualização via `/clients/me`)

**Ação:** Avaliar se faz sentido manter para uso administrativo futuro ou remover definitivamente.

---

### Endpoints que DEVEM ser mantidos para futuras funcionalidades:
1. ✅ `POST /professionals/{id}/reviews` - Sistema de avaliações (funcionalidade comum em plataformas de serviços)
2. ✅ `PUT /bookings/{id}` - Reagendamento (funcionalidade essencial em sistemas de agendamento)
3. ✅ `POST /departments`, `PUT /departments/{id}`, `DELETE /departments/{id}` - CRUD completo para departamentos (pode ser útil para admin)

---

## 📊 Estatísticas Atualizadas

### **Antes do Dashboard Admin:**
- Endpoints utilizados: 26/36 (72%)
- Endpoints não utilizados: 10/36 (28%)

### **Depois do Dashboard Admin:**
- Endpoints utilizados: 33/36 (92%) 🎉
- Endpoints não utilizados: 3/36 (8%)
- Endpoints redundantes: 2/36 (5.5%)

### **Melhoria:**
- **+20% de utilização!** 🚀
- **8 novos endpoints agora sendo utilizados**

---

**Última atualização:** Janeiro 2025  
**Atualizado:** Inclui análise do Dashboard Admin
