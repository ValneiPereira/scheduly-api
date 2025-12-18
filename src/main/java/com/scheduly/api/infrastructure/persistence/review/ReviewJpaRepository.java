package com.scheduly.api.infrastructure.persistence.review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ProfessionalReviewEntity, Long> {
}
