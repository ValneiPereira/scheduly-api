CREATE TABLE IF NOT EXISTS professional_reviews (
    id SERIAL PRIMARY KEY,
    professional_id BIGINT NOT NULL,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_professional FOREIGN KEY (professional_id) REFERENCES professionals(id) ON DELETE CASCADE
);

CREATE INDEX idx_professional_reviews_professional ON professional_reviews(professional_id);
CREATE INDEX idx_professional_reviews_rating ON professional_reviews(rating);
