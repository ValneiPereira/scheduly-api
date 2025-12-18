package com.scheduly.api.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalReview {
    private Long id;
    private Long professionalId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
