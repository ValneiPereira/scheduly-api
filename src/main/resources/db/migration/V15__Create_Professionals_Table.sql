CREATE TABLE IF NOT EXISTS professionals (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    phone VARCHAR(15),
    primary_address_id BIGINT,
    bio VARCHAR(500),
    rating DECIMAL(2, 1),
    total_reviews INTEGER DEFAULT 0,
    work_start_time TIME NOT NULL,
    work_end_time TIME NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_professional_address FOREIGN KEY (primary_address_id) REFERENCES addresses(id) ON DELETE SET NULL
);

CREATE INDEX idx_professionals_email ON professionals(email);
CREATE INDEX idx_professionals_cpf ON professionals(cpf);
CREATE INDEX idx_professionals_active ON professionals(active);
CREATE INDEX idx_professionals_address ON professionals(primary_address_id);
