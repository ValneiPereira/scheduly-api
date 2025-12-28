-- Criar tabela de endereços
CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(20),
    complement VARCHAR(100),
    neighborhood VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(2) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    owner_id BIGINT,
    owner_type VARCHAR(20), -- CLIENT or PROFESSIONAL
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Adicionar FK de endereço em bookings
ALTER TABLE bookings ADD COLUMN address_id BIGINT;
ALTER TABLE bookings ADD CONSTRAINT fk_bookings_address FOREIGN KEY (address_id) REFERENCES addresses(id);

-- Adicionar endereço primário em clients e professionals
ALTER TABLE clients ADD COLUMN primary_address_id BIGINT;
ALTER TABLE clients ADD CONSTRAINT fk_clients_primary_address FOREIGN KEY (primary_address_id) REFERENCES addresses(id);

ALTER TABLE professionals ADD COLUMN primary_address_id BIGINT;
ALTER TABLE professionals ADD CONSTRAINT fk_professionals_primary_address FOREIGN KEY (primary_address_id) REFERENCES addresses(id);

-- Remover colunas de endereço
-- Para o MVP, podemos manter mas marcar como obsoletas se quisermos, 
-- mas como o usuário pediu pra refatorar, vamos remover depois de migrar dados (se houver).
-- Como acabamos de limpar a API, vamos remover direto.

ALTER TABLE clients DROP COLUMN street;
ALTER TABLE clients DROP COLUMN number;
ALTER TABLE clients DROP COLUMN complement;
ALTER TABLE clients DROP COLUMN neighborhood;
ALTER TABLE clients DROP COLUMN city;
ALTER TABLE clients DROP COLUMN state;
ALTER TABLE clients DROP COLUMN zip_code;

ALTER TABLE professionals DROP COLUMN street;
ALTER TABLE professionals DROP COLUMN number;
ALTER TABLE professionals DROP COLUMN complement;
ALTER TABLE professionals DROP COLUMN neighborhood;
ALTER TABLE professionals DROP COLUMN city;
ALTER TABLE professionals DROP COLUMN state;
ALTER TABLE professionals DROP COLUMN zip_code;
