# 🐳 Docker & CI/CD - Scheduly API

## 📋 Guia Rápido

### **Executar com Docker Compose (Recomendado)**

```bash
# Iniciar todos os serviços (PostgreSQL + API + pgAdmin)
docker compose up -d

# Ver logs
docker compose logs -f api

# Parar todos os serviços
docker compose down

# Parar e remover volumes (limpa banco de dados)
docker compose down -v
```

### **Acessar Serviços:**
- **API:** http://localhost:8080
- **Swagger:** http://localhost:8080/swagger-ui.html
- **pgAdmin:** http://localhost:8089
  - Email: `user@domain.com`
  - Senha: `123456`

### **Conectar pgAdmin ao PostgreSQL:**
1. Abra http://localhost:8089
2. Clique em "Add New Server"
3. **General Tab:**
   - Name: `Scheduly DB`
4. **Connection Tab:**
   - Host: `postgres`
   - Port: `5432`
   - Database: `banco`
   - Username: `postgres`
   - Password: `postgres`

---

## 🔧 Desenvolvimento Local

### **Opção 1: Docker Compose (Desenvolvimento)**

```bash
# Build e iniciar
docker-compose up --build

# Rebuild apenas a API
docker-compose up --build api
```

### **Opção 2: Apenas Banco de Dados no Docker**

```bash
# Iniciar apenas PostgreSQL e pgAdmin
docker-compose up postgres pgadmin

# Em outro terminal, rodar a API localmente
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 🏗️ Build Manual do Docker

### **Build da Imagem:**

```bash
# Build de produção (multi-stage, otimizado)
docker build -t scheduly-api:latest .

# Build de desenvolvimento (com hot reload)
docker build -f Dockerfile.dev -t scheduly-api:dev .
```

### **Executar Container:**

```bash
# Produção
docker run -d \
  --name scheduly-api \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/banco \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  scheduly-api:latest

# Desenvolvimento
docker run -d \
  --name scheduly-api-dev \
  -p 8080:8080 \
  -v $(pwd)/src:/app/src \
  scheduly-api:dev
```

---

## 🚀 CI/CD Workflows

### **1. CI - Build, Test & Analysis** (`.github/workflows/ci.yml`)

**Triggers:**
- Push para `master`, `main`, `develop`
- Pull Requests

**Jobs:**
- ✅ Build e testes unitários
- ✅ Testes de integração
- ✅ Cobertura de código (JaCoCo)
- ✅ Análise de qualidade (SpotBugs)
- ✅ Scan de segurança (OWASP Dependency Check)
- ✅ Build de Docker image
- ✅ Scan de vulnerabilidades (Trivy)

**Artefatos gerados:**
- Relatórios de teste
- Relatório de cobertura
- JAR compilado
- Relatório de segurança

### **2. Deploy to Staging** (`.github/workflows/deploy-staging.yml`)

**Trigger:**
- Push para `develop`
- Manual (workflow_dispatch)

**Status:** ⚠️ Preparado mas não configurado

**Para ativar:**
1. Descomentar seções de deploy
2. Configurar secrets no GitHub
3. Escolher plataforma (Render, Railway, etc)

### **3. Deploy to Production** (`.github/workflows/deploy-production.yml`)

**Trigger:**
- Manual apenas (workflow_dispatch)
- Requer especificar versão

**Status:** ⚠️ Preparado mas não configurado

**Para ativar:**
1. Descomentar seções de deploy
2. Configurar secrets no GitHub
3. Configurar ambiente de produção

---

## 🔐 Secrets do GitHub

Para ativar os workflows de deploy, configure estes secrets:

```
Settings → Secrets and variables → Actions → New repository secret
```

**Secrets necessários:**
- `DOCKER_USERNAME` - Usuário do Docker Hub
- `DOCKER_PASSWORD` - Senha/Token do Docker Hub

**Para deploy (quando configurar):**
- Render: `RENDER_API_KEY`
- Railway: `RAILWAY_TOKEN`
- AWS: `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`

---

## 📊 Análise de Código

### **Ferramentas Ativas no CI:**

✅ **SpotBugs** - Detecta bugs e problemas comuns  
✅ **Checkstyle** - Verifica estilo de código  
✅ **OWASP Dependency Check** - Analisa vulnerabilidades em dependências  
✅ **Trivy** - Scan de segurança em imagens Docker  
✅ **JaCoCo** - Cobertura de testes  

### **Executar Análises Localmente:**

```bash
# SpotBugs
mvn spotbugs:check

# Checkstyle
mvn checkstyle:check

# OWASP Dependency Check
mvn dependency-check:check
```

**Relatórios gerados em:**
- SpotBugs: `target/spotbugsXml.xml`
- Checkstyle: `target/checkstyle-result.xml`
- OWASP: `target/dependency-check-report.html`

---

## 🧪 Testes

### **Executar Testes Localmente:**

```bash
# Testes unitários
mvn test

# Testes de integração
mvn verify

# Todos os testes
mvn clean verify

# Com cobertura
mvn clean verify jacoco:report
```

### **Ver Cobertura:**
Abra: `target/site/jacoco/index.html`

---

## 🐛 Troubleshooting

### **Docker Compose não inicia:**
```bash
# Verificar logs
docker-compose logs

# Rebuild completo
docker-compose down -v
docker-compose build --no-cache
docker-compose up
```

### **API não conecta no banco:**
```bash
# Verificar se PostgreSQL está rodando
docker-compose ps

# Verificar logs do banco
docker-compose logs postgres

# Testar conexão
docker-compose exec postgres psql -U postgres -d banco
```

### **Porta 8080 já em uso:**
```bash
# Alterar porta no docker-compose.yml
ports:
  - "8081:8080"  # Usar 8081 no host
```

---

## 📝 Comandos Úteis

```bash
# Ver containers rodando
docker-compose ps

# Acessar shell do container
docker-compose exec api sh

# Ver logs em tempo real
docker-compose logs -f api

# Rebuild apenas um serviço
docker-compose up --build api

# Limpar tudo (cuidado!)
docker-compose down -v --rmi all

# Executar comando no container
docker-compose exec api java -version
```

---

## 🎯 Próximos Passos

- [ ] Adicionar mais testes de integração
- [ ] Configurar deploy automático (Render/Railway)
- [ ] Implementar cache de dependências Maven no CI
- [ ] Adicionar notificações (Slack/Discord)
- [ ] Configurar monitoramento (Prometheus/Grafana)
- [ ] Implementar migrations com Flyway

---

**Última atualização:** 2025-12-02
