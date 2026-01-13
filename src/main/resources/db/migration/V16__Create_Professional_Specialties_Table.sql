CREATE TABLE IF NOT EXISTS professional_specialties (
    professional_id BIGINT NOT NULL,
    specialty_id BIGINT NOT NULL,
    PRIMARY KEY (professional_id, specialty_id),
    CONSTRAINT fk_professional_specialty_professional FOREIGN KEY (professional_id) REFERENCES professionals(id) ON DELETE CASCADE
);

CREATE INDEX idx_professional_specialties_professional ON professional_specialties(professional_id);
CREATE INDEX idx_professional_specialties_specialty ON professional_specialties(specialty_id);
