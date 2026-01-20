-- Migration: Adicionar campo avatar_url na tabela clients
-- Autor: Sistema
-- Data: 2026-01-16
-- Descrição: Adiciona coluna para armazenar a URL do avatar do cliente (Cloudinary)

ALTER TABLE clients
ADD COLUMN avatar_url VARCHAR(500);

COMMENT ON COLUMN clients.avatar_url IS 'URL do avatar do cliente armazenado no Cloudinary';
