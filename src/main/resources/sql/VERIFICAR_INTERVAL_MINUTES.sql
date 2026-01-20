-- ============================================
-- Consultas para verificar a coluna interval_minutes
-- ============================================

-- 1. Verificar se a coluna existe na tabela professionals
SELECT 
    column_name,
    data_type,
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'professionals'
  AND column_name = 'interval_minutes';

-- 2. Verificar a estrutura completa da tabela professionals
SELECT 
    column_name,
    data_type,
    character_maximum_length,
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'professionals'
ORDER BY ordinal_position;

-- 3. Verificar profissionais existentes e seus interval_minutes
SELECT 
    id,
    name,
    email,
    work_start_time,
    work_end_time,
    interval_minutes,
    active,
    created_at
FROM professionals
ORDER BY id DESC
LIMIT 10;

-- 4. Verificar se há profissionais com interval_minutes NULL (não deveria ter)
SELECT 
    id,
    name,
    email,
    interval_minutes
FROM professionals
WHERE interval_minutes IS NULL;

-- 5. Verificar profissionais com interval_minutes diferente do padrão (30)
SELECT 
    id,
    name,
    email,
    interval_minutes,
    work_start_time,
    work_end_time
FROM professionals
WHERE interval_minutes != 30 OR interval_minutes IS NULL;

-- 6. Estatísticas dos interval_minutes
SELECT 
    interval_minutes,
    COUNT(*) as quantidade
FROM professionals
GROUP BY interval_minutes
ORDER BY interval_minutes;
