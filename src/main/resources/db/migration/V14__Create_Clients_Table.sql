CREATE TABLE IF NOT EXISTS clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    phone VARCHAR(15),
    primary_address_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_client_address FOREIGN KEY (primary_address_id) REFERENCES addresses(id) ON DELETE SET NULL
);

CREATE INDEX idx_clients_email ON clients(email);
CREATE INDEX idx_clients_cpf ON clients(cpf);
CREATE INDEX idx_clients_address ON clients(primary_address_id);
