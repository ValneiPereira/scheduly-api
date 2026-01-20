-- Remove coluna CPF da tabela clients
ALTER TABLE clients DROP COLUMN IF EXISTS cpf;

-- Remove coluna CPF da tabela professionals
ALTER TABLE professionals DROP COLUMN IF EXISTS cpf;
