package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.review.ProfessionalReview;
import com.scheduly.api.web.dtos.ReviewRequest;
import com.scheduly.api.web.dtos.ReviewResponse;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ProfessionalReview toDomain(ReviewRequest request, Long professionalId) {
        if (request == null)
            return null;
        return ProfessionalReview.builder()
                .professionalId(professionalId)
                .rating(request.rating())
                .comment(request.comment())
                .build();
    }

    public ReviewResponse toResponse(ProfessionalReview domain) {
        if (domain == null)
            return null;
        return new ReviewResponse(
                domain.getId(),
                domain.getProfessionalId(),
                domain.getRating(),
                domain.getComment(),
                domain.getCreatedAt());
    }
}
