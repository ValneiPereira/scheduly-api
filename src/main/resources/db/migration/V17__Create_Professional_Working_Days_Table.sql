CREATE TABLE IF NOT EXISTS professional_working_days (
    professional_id BIGINT NOT NULL,
    working_day VARCHAR(20) NOT NULL,
    PRIMARY KEY (professional_id, working_day),
    CONSTRAINT fk_professional_working_days_professional FOREIGN KEY (professional_id) REFERENCES professionals(id) ON DELETE CASCADE
);

CREATE INDEX idx_professional_working_days_professional ON professional_working_days(professional_id);
