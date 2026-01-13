# 🚀 Guia Rápido - Cadastrar Profissionais/Especialistas

## ⚠️ Problema Identificado

A tela mostrada é de **seleção de especialista para agendamento**, não de cadastro. Para que os especialistas apareçam nessa tela, você precisa **cadastrá-los primeiro via API**.

## ✅ Solução: Cadastrar Profissionais via API

### Passo 1: Fazer Login

**POST** `http://localhost:8080/auth/login`

Body:
```json
{
    "email": "admin@studio.com",
    "password": "senha123"
}
```

**Copie o `accessToken` da resposta!**

---

### Passo 2: Criar Departamento/Serviço (Se ainda não criou)

Antes de criar o profissional, você precisa ter pelo menos um departamento/serviço cadastrado, pois o profissional precisa ter `specialtyIds`.

**POST** `http://localhost:8080/departments`

Headers:
```
Authorization: Bearer {seu_accessToken}
Content-Type: application/json
```

Body:
```json
{
    "name": "Manicure Básica",
    "description": "Manicure tradicional com esmaltação",
    "category": "BELEZA",
    "subcategory": "MANICURE",
    "durationMinutes": 45,
    "priceCents": 3500
}
```

**Copie o `id` do departamento criado!** (Exemplo: `id: 1`)

---

### Passo 3: Criar Profissional/Especialista

**POST** `http://localhost:8080/professionals`

Headers:
```
Authorization: Bearer {seu_accessToken}
Content-Type: application/json
```

Body (exemplo completo):
```json
{
    "name": "Joana Pereira",
    "email": "joana.pereira@email.com",
    "cpf": "55566677788",
    "phone": "11988887777",
    "bio": "Especialista em alongamento e nail art com mais de 10 anos de experiência",
    "specialtyIds": [1],
    "workStartTime": "09:00",
    "workEndTime": "19:00",
    "workingDays": ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"],
    "active": true,
    "address": {
        "street": "Rua da Beleza",
        "number": "456",
        "neighborhood": "Jardim Paulista",
        "city": "São Paulo",
        "state": "SP",
        "zipCode": "01415-000"
    }
}
```

**Importante:**
- `specialtyIds`: Use o ID do departamento criado no Passo 2
- `workStartTime` e `workEndTime`: Formato `HH:mm` (ex: "09:00", "19:00")
- `workingDays`: Array com dias em maiúsculas: `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY`
- `cpf`: Deve ser válido (formato CPF brasileiro)

---

### Passo 4: Verificar se Funcionou

**GET** `http://localhost:8080/professionals`

Headers:
```
Authorization: Bearer {seu_accessToken}
```

Se retornar a lista com o profissional criado, está funcionando! Agora ele deve aparecer na tela do app.

---

## 📋 Exemplo Completo - Criar 3 Profissionais

### Profissional 1:
```json
{
    "name": "Joana Pereira",
    "email": "joana.pereira@email.com",
    "cpf": "55566677788",
    "phone": "11988887777",
    "bio": "Especialista em alongamento e nail art",
    "specialtyIds": [1],
    "workStartTime": "09:00",
    "workEndTime": "19:00",
    "workingDays": ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"],
    "active": true,
    "address": {
        "street": "Rua da Beleza",
        "number": "456",
        "neighborhood": "Jardim Paulista",
        "city": "São Paulo",
        "state": "SP",
        "zipCode": "01415-000"
    }
}
```

### Profissional 2:
```json
{
    "name": "Carla Mendes",
    "email": "carla.mendes@email.com",
    "cpf": "99988877766",
    "phone": "11977778888",
    "bio": "Manicure especializada em esmaltação em gel",
    "specialtyIds": [1],
    "workStartTime": "08:00",
    "workEndTime": "18:00",
    "workingDays": ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"],
    "active": true,
    "address": {
        "street": "Rua Oscar Freire",
        "number": "789",
        "neighborhood": "Jardins",
        "city": "São Paulo",
        "state": "SP",
        "zipCode": "01426-001"
    }
}
```

### Profissional 3:
```json
{
    "name": "Beatriz Lima",
    "email": "beatriz.lima@email.com",
    "cpf": "44455566677",
    "phone": "11966667777",
    "bio": "Designer de unhas com foco em nail art personalizada",
    "specialtyIds": [1],
    "workStartTime": "10:00",
    "workEndTime": "20:00",
    "workingDays": ["TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"],
    "active": true,
    "address": {
        "street": "Alameda Santos",
        "number": "1001",
        "neighborhood": "Jardim Paulista",
        "city": "São Paulo",
        "state": "SP",
        "zipCode": "01418-001"
    }
}
```

---

## 🔍 Usando a Collection do Postman

1. Importe a collection `Scheduly-API.postman_collection.json`
2. Execute **Login** na pasta 🔐 Autenticação
3. O token será salvo automaticamente
4. Vá em 💼 Profissionais → **Criar Profissional**
5. Use os dados de exemplo acima no body
6. Clique em **Send**

---

## ⚠️ Erros Comuns

### Erro 400 - Campos obrigatórios
- Verifique se preencheu todos os campos obrigatórios:
  - `name`, `email`, `cpf`, `phone`
  - `workStartTime`, `workEndTime`, `workingDays`, `active`
  - `specialtyIds` (pelo menos um)

### Erro 401 - Não autorizado
- Faça login novamente
- Verifique se o token está no header: `Authorization: Bearer {token}`

### Erro 409 - Conflito
- Email ou CPF já cadastrado
- Use um email/CPF diferente

### Erro 400 - CPF inválido
- Use um CPF válido (pode ser de teste, mas deve seguir formato)
- Exemplo válido: `12345678900` ou `123.456.789-00`

### Erro 400 - Horários inválidos
- `workStartTime` deve ser antes de `workEndTime`
- Formato: `HH:mm` (ex: "09:00", não "9:00")

---

## 📱 Depois de Cadastrar

Após cadastrar os profissionais via API:
1. Volte para a tela do app
2. Faça refresh/pull to refresh
3. Os profissionais devem aparecer na lista
4. Agora você pode selecionar um para agendar

---

## 💡 Dica Rápida

**Use o Postman para cadastrar dados iniciais rapidamente!**

A collection já vem com exemplos prontos. Basta:
1. Fazer login
2. Criar departamento
3. Criar profissionais
4. Testar no app
