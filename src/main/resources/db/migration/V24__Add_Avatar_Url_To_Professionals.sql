-- Adiciona coluna avatar_url na tabela professionals
ALTER TABLE professionals
ADD COLUMN avatar_url VARCHAR(500);

COMMENT ON COLUMN professionals.avatar_url IS 'URL do avatar do profissional armazenado no Cloudinary';
