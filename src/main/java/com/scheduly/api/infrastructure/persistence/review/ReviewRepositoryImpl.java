package com.scheduly.api.infrastructure.persistence.review;

import com.scheduly.api.domain.review.ProfessionalReview;
import com.scheduly.api.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository jpaRepository;

    @Override
    public ProfessionalReview save(ProfessionalReview review) {
        ProfessionalReviewEntity entity = toEntity(review);
        var savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    private ProfessionalReviewEntity toEntity(ProfessionalReview domain) {
        return ProfessionalReviewEntity.builder()
                .id(domain.getId())
                .professionalId(domain.getProfessionalId())
                .rating(domain.getRating())
                .comment(domain.getComment())
                .build();
    }

    private ProfessionalReview toDomain(ProfessionalReviewEntity entity) {
        return ProfessionalReview.builder()
                .id(entity.getId())
                .professionalId(entity.getProfessionalId())
                .rating(entity.getRating())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
