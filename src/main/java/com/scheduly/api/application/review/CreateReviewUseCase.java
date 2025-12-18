package com.scheduly.api.application.review;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.exception.ValidationException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.review.ProfessionalReview;
import com.scheduly.api.domain.review.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class CreateReviewUseCase {

    private final ReviewRepository reviewRepository;
    private final ProfessionalRepository professionalRepository;

    @Transactional
    public ProfessionalReview execute(ProfessionalReview review) {
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new ValidationException("A nota deve ser um número inteiro entre 1 e 5.");
        }

        Professional professional = professionalRepository.findById(review.getProfessionalId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profissional não encontrado com ID: " + review.getProfessionalId()));

        ProfessionalReview savedReview = reviewRepository.save(review);
        savedReview.setCreatedAt(now()); // Setar data para retorno (Repo pode ter salvo)

        updateProfessionalRating(professional, review.getRating());
        professionalRepository.save(professional);

        return savedReview;
    }

    private void updateProfessionalRating(Professional professional, Integer newRating) {
        int currentTotal = professional.getTotalReviews() != null ? professional.getTotalReviews() : 0;
        BigDecimal currentRating = professional.getRating() != null ? professional.getRating() : BigDecimal.ZERO;

        BigDecimal totalScore = currentRating.multiply(BigDecimal.valueOf(currentTotal));
        totalScore = totalScore.add(BigDecimal.valueOf(newRating));

        int newTotal = currentTotal + 1;
        BigDecimal newAverage = totalScore.divide(BigDecimal.valueOf(newTotal), 1, RoundingMode.HALF_UP);

        professional.setTotalReviews(newTotal);
        professional.setRating(newAverage);
    }
}
