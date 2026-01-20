-- Adiciona coluna interval_minutes na tabela professionals
ALTER TABLE professionals
ADD COLUMN interval_minutes INTEGER NOT NULL DEFAULT 30;

-- Comentário na coluna
COMMENT ON COLUMN professionals.interval_minutes IS 'Intervalo entre atendimentos em minutos';
