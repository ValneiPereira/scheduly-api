CREATE TABLE IF NOT EXISTS addresses (
    id SERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(50),
    complement VARCHAR(255),
    neighborhood VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(2) NOT NULL,
    zip_code VARCHAR(9) NOT NULL,
    owner_id BIGINT,
    owner_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
