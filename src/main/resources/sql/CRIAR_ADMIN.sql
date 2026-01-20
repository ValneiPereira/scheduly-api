-- Script para criar ou atualizar um usuário ADMIN
-- Execute este script no seu banco de dados

-- OPÇÃO 1: Se você já tem um usuário cadastrado, apenas atualize o role
-- Substitua 'seu@email.com' pelo email do usuário que você quer tornar ADMIN
UPDATE users 
SET role = 'ADMIN' 
WHERE email = 'seu@email.com';

-- OPÇÃO 2: Criar um novo usuário ADMIN diretamente no banco
-- ATENÇÃO: Você precisará gerar o hash da senha primeiro!
-- Use o mesmo método que a API usa para hashear senhas (BCrypt)
-- Por exemplo, se a senha for 'admin123', o hash BCrypt seria algo como: $2a$10$...
-- 
-- INSERT INTO users (email, password, role, created_at, updated_at)
-- VALUES ('admin@scheduly.com', '$2a$10$...', 'ADMIN', NOW(), NOW());

-- Para verificar se funcionou:
SELECT id, email, role FROM users WHERE role = 'ADMIN';
