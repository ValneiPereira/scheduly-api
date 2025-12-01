# 🌳 Estratégia de Branching - Scheduly API

## 📋 Git Flow Simplificado

Este projeto utiliza uma estratégia de branching baseada no **Git Flow**, adaptada para times pequenos.

---

## 🌿 Estrutura de Branches

### **Branches Principais (Permanentes)**

#### `master` (ou `main`)
- **Propósito:** Código em produção
- **Proteção:** ✅ Protegida, requer PR aprovado
- **Deploy:** Automático para produção
- **Merge de:** `develop` (via release) ou `hotfix`
- **Nunca commitar diretamente!**

#### `develop`
- **Propósito:** Integração de features, código de desenvolvimento
- **Proteção:** ✅ Protegida, requer PR
- **Deploy:** Automático para staging
- **Merge de:** `feature/*`, `bugfix/*`
- **Base para:** Novas features

---

### **Branches Temporárias (Deletadas após merge)**

#### `feature/*`
- **Nomenclatura:** `feature/EPIC-TASK-descricao`
- **Exemplos:**
  - `feature/AUTH-001-jwt-implementation`
  - `feature/CLIENT-003-crud-endpoints`
  - `feature/BOOKING-005-availability-logic`
- **Criada de:** `develop`
- **Merge para:** `develop`
- **Duração:** Até conclusão da feature

#### `bugfix/*`
- **Nomenclatura:** `bugfix/ISSUE-descricao`
- **Exemplos:**
  - `bugfix/123-fix-booking-validation`
  - `bugfix/456-null-pointer-client-service`
- **Criada de:** `develop`
- **Merge para:** `develop`

#### `hotfix/*`
- **Nomenclatura:** `hotfix/versao-descricao`
- **Exemplos:**
  - `hotfix/1.0.1-critical-security-fix`
  - `hotfix/1.0.2-booking-crash`
- **Criada de:** `master`
- **Merge para:** `master` E `develop`
- **Uso:** Apenas para bugs críticos em produção

#### `release/*`
- **Nomenclatura:** `release/v1.0.0`
- **Criada de:** `develop`
- **Merge para:** `master` E `develop`
- **Uso:** Preparação de release (ajustes finais, versioning)

---

## 🔄 Fluxo de Trabalho

### **1. Iniciar Nova Feature**

```bash
# Atualizar develop
git checkout develop
git pull origin develop

# Criar branch de feature
git checkout -b feature/AUTH-001-jwt-implementation

# Trabalhar na feature...
git add .
git commit -m "feat(auth): implement JWT token generation"

# Push para remote
git push origin feature/AUTH-001-jwt-implementation
```

### **2. Finalizar Feature**

```bash
# Atualizar com develop (rebase)
git checkout develop
git pull origin develop
git checkout feature/AUTH-001-jwt-implementation
git rebase develop

# Criar Pull Request no GitHub
# Após aprovação, merge via GitHub (squash ou merge commit)

# Deletar branch local
git branch -d feature/AUTH-001-jwt-implementation
```

### **3. Hotfix Urgente**

```bash
# Criar hotfix de master
git checkout master
git pull origin master
git checkout -b hotfix/1.0.1-critical-bug

# Corrigir bug
git add .
git commit -m "fix: critical security vulnerability"

# Merge para master
git checkout master
git merge hotfix/1.0.1-critical-bug
git tag v1.0.1
git push origin master --tags

# Merge para develop também
git checkout develop
git merge hotfix/1.0.1-critical-bug
git push origin develop

# Deletar branch
git branch -d hotfix/1.0.1-critical-bug
```

### **4. Release**

```bash
# Criar release de develop
git checkout develop
git pull origin develop
git checkout -b release/v1.0.0

# Ajustes finais (versioning, changelog)
git commit -m "chore: prepare release v1.0.0"

# Merge para master
git checkout master
git merge release/v1.0.0
git tag v1.0.0
git push origin master --tags

# Merge de volta para develop
git checkout develop
git merge release/v1.0.0
git push origin develop

# Deletar branch
git branch -d release/v1.0.0
```

---

## 📝 Convenção de Commits (Conventional Commits)

### Formato:
```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types:
- `feat`: Nova funcionalidade
- `fix`: Correção de bug
- `docs`: Documentação
- `style`: Formatação (sem mudança de código)
- `refactor`: Refatoração
- `test`: Testes
- `chore`: Tarefas de manutenção

### Exemplos:
```bash
feat(auth): add JWT authentication
fix(booking): resolve null pointer in availability check
docs(readme): update installation instructions
refactor(client): extract validation to separate service
test(booking): add unit tests for conflict detection
chore(deps): update spring boot to 3.4.12
```

---

## 🔒 Proteção de Branches

### Configurar no GitHub:

**Para `master`:**
- ✅ Require pull request before merging
- ✅ Require approvals (1+)
- ✅ Require status checks to pass
- ✅ Require branches to be up to date
- ✅ Include administrators
- ❌ Allow force pushes

**Para `develop`:**
- ✅ Require pull request before merging
- ✅ Require status checks to pass
- ❌ Allow force pushes

---

## 🏷️ Nomenclatura de Branches por Epic

### Epic 1: Infraestrutura
- `feature/INFRA-001-project-setup`
- `feature/INFRA-002-openapi-config`
- `feature/INFRA-003-exception-handler`

### Epic 2: Autenticação
- `feature/AUTH-001-jwt-implementation`
- `feature/AUTH-002-spring-security-config`
- `feature/AUTH-003-user-management`

### Epic 3: Gestão de Clientes
- `feature/CLIENT-001-entity-repository`
- `feature/CLIENT-002-use-cases`
- `feature/CLIENT-003-controllers`

### Epic 4: Gestão de Profissionais
- `feature/PROF-001-entity-repository`
- `feature/PROF-002-schedule-management`
- `feature/PROF-003-controllers`

### Epic 5: Gestão de Serviços
- `feature/SERVICE-001-entity-repository`
- `feature/SERVICE-002-use-cases`
- `feature/SERVICE-003-catalog`

### Epic 6: Agendamentos
- `feature/BOOKING-001-entity-repository`
- `feature/BOOKING-002-business-rules`
- `feature/BOOKING-003-availability-logic`
- `feature/BOOKING-004-controllers`

### Epic 7: Notificações
- `feature/NOTIF-001-email-service`
- `feature/NOTIF-002-sms-service`
- `feature/NOTIF-003-templates`
- `feature/NOTIF-004-scheduler`

### Epic 8: Integrações
- `feature/INTEG-001-viacep`
- `feature/INTEG-002-whatsapp`

### Epic 9: Testes e Deploy
- `feature/TEST-001-integration-tests`
- `feature/DEPLOY-001-docker-config`
- `feature/DEPLOY-002-ci-cd`

---

## 📊 Visualização do Fluxo

```
master    ──●────────────────●────────────●──────→ (produção)
             │                │            │
             │            (release)    (hotfix)
             │                │            │
develop   ───●────●────●─────●────────────●──────→ (staging)
                  │    │
                  │    └─ feature/CLIENT-001
                  └────── feature/AUTH-001
```

---

## ✅ Checklist de PR (Pull Request)

Antes de criar um PR, verifique:

- [ ] Código compilando sem erros
- [ ] Testes unitários passando
- [ ] Testes de integração passando
- [ ] Code coverage mantido/aumentado
- [ ] Documentação atualizada
- [ ] Sem código comentado
- [ ] Sem console.log ou prints de debug
- [ ] Commit messages seguem convenção
- [ ] Branch atualizada com develop

---

## 🚀 Comandos Úteis

```bash
# Ver todas as branches
git branch -a

# Deletar branch local
git branch -d nome-da-branch

# Deletar branch remota
git push origin --delete nome-da-branch

# Atualizar lista de branches remotas
git fetch --prune

# Ver histórico gráfico
git log --oneline --graph --all

# Desfazer último commit (mantém alterações)
git reset --soft HEAD~1

# Atualizar branch com develop (rebase)
git rebase develop

# Resolver conflitos e continuar rebase
git add .
git rebase --continue
```

---

## 📚 Referências

- [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [GitHub Flow](https://guides.github.com/introduction/flow/)

---

**Última atualização:** 2025-12-01
