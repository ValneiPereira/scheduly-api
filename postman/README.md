# 📬 Scheduly API - Collection Postman

Este diretório contém arquivos para facilitar o teste da API Scheduly no Postman.

## 📁 Arquivos

### 1. `Scheduly-API.postman_collection.json`
Collection completa do Postman com todos os endpoints da API organizados por categorias:

- 🔐 **Autenticação**: Login, registro, refresh token
- 👥 **Clientes**: CRUD completo de clientes
- 💼 **Profissionais**: CRUD, avaliações
- 💅 **Departamentos**: CRUD de serviços/departamentos
- 📅 **Agendamentos**: CRUD, filtros, mudança de status
- 🔧 **Utilitários**: Consulta CEP

### 2. `dados-teste.json`
Arquivo JSON com massa de dados para testes, incluindo:

- Dados de autenticação
- Exemplos de clientes
- Exemplos de profissionais
- Exemplos de departamentos/serviços
- Exemplos de agendamentos
- CEPs para teste
- Enums e constantes
- Cenários de teste
- Mensagens de erro comuns

## 🚀 Como Usar

### Importar Collection no Postman

1. Abra o Postman
2. Clique em **Import** (canto superior esquerdo)
3. Selecione o arquivo `Scheduly-API.postman_collection.json`
4. A collection será importada com todas as requisições organizadas

### Configurar Variáveis

A collection já vem configurada com variáveis:

- `baseUrl`: URL base da API (padrão: `http://localhost:8080`)
- `accessToken`: Token JWT (preenchido automaticamente após login)

Para alterar a URL base:
1. Clique nos 3 pontos ao lado da collection
2. Selecione **Edit**
3. Vá na aba **Variables**
4. Altere o valor de `baseUrl` se necessário

### Autenticação Automática

A collection está configurada para:
- **Login** salvar automaticamente o token nas variáveis
- Todas as requisições protegidas usarem o token automaticamente via Bearer Auth

### Usar Dados de Teste

1. Importe a collection
2. Abra qualquer requisição
3. No body da requisição, você pode copiar os exemplos do arquivo `dados-teste.json`
4. Ajuste os IDs conforme necessário (IDs retornados ao criar recursos)

## 📋 Fluxo Recomendado de Testes

### 1. Autenticação
```
POST /auth/login
```
Use as credenciais do arquivo `dados-teste.json` → `autenticacao.login.admin`

### 2. Criar Recursos Base
```
POST /clients          (criar cliente)
POST /professionals    (criar profissional)
POST /departments      (criar serviço)
```

### 3. Criar Agendamento
```
POST /bookings
```
Use os IDs retornados nos passos anteriores.

### 4. Testar Outras Operações
```
GET /bookings          (listar com filtros)
PUT /bookings/{id}     (reagendar ou mudar status)
POST /professionals/{id}/reviews  (avaliar)
```

## 🔍 Filtros Disponíveis

### Agendamentos
- `?clientId=1` - Filtrar por cliente
- `?professionalId=1` - Filtrar por profissional
- `?date=2025-01-15` - Filtrar por data

### Profissionais
- `?departmentId=1` - Filtrar por departamento/serviço

### Departamentos
- `?category=BELEZA` - Filtrar por categoria

## ⚠️ Validações Importantes

Ao testar, considere:

1. **Horários de Trabalho**: Profissionais têm horários definidos (ex: 09:00 às 19:00)
2. **Dias de Trabalho**: Profissionais trabalham em dias específicos (ex: segunda a sexta)
3. **Conflitos**: Não é possível agendar dois serviços no mesmo horário para o mesmo profissional
4. **Datas**: Não é possível agendar no passado

## 📝 Notas

- Todos os endpoints (exceto `/auth/*` e `/cep/*`) requerem autenticação JWT
- O token é válido por 15 minutos (padrão)
- Use `/auth/refresh` para renovar o token
- IDs retornados ao criar recursos devem ser salvos para usar em outras requisições

## 🐛 Troubleshooting

### Erro 401 (Unauthorized)
- Verifique se fez login e o token foi salvo
- Tente fazer login novamente

### Erro 400 (Bad Request)
- Verifique se todos os campos obrigatórios foram preenchidos
- Verifique formatos (email, CPF, telefone, CEP, etc.)

### Erro 409 (Conflict)
- Horário já ocupado
- Email/CPF duplicado
- Agendamento fora do horário de trabalho

### Erro 404 (Not Found)
- Recurso não existe
- Verifique se o ID está correto

## 📚 Estrutura da API

Base URL: `http://localhost:8080`

Endpoints principais:
- `/auth/*` - Autenticação
- `/clients/*` - Clientes
- `/professionals/*` - Profissionais
- `/departments/*` - Departamentos/Serviços
- `/bookings/*` - Agendamentos
- `/cep/*` - Utilitários (CEP)

Para mais detalhes, consulte a documentação OpenAPI em `src/main/resources/swagger/api.yaml` ou acesse o Swagger UI em `http://localhost:8080/swagger-ui.html` (se configurado).
