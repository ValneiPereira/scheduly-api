-- Criação da tabela de refresh tokens
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(500) NOT NULL UNIQUE,
    user_email VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_user_email ON refresh_tokens(user_email);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_expiry_date ON refresh_tokens(expiry_date);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_token ON refresh_tokens(token);

-- Comentários
COMMENT ON TABLE refresh_tokens IS 'Armazena refresh tokens para renovação de access tokens';
COMMENT ON COLUMN refresh_tokens.token IS 'JWT refresh token';
COMMENT ON COLUMN refresh_tokens.user_email IS 'Email do usuário dono do token';
COMMENT ON COLUMN refresh_tokens.expiry_date IS 'Data de expiração do refresh token';
COMMENT ON COLUMN refresh_tokens.created_at IS 'Data de criação do token';
