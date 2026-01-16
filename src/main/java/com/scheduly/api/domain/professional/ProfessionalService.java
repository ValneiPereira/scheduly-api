package com.scheduly.api.domain.professional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalService {
    private Long professionalId;
    private Long departmentId;
    private Integer priceCents;
    private Integer durationMinutes;
    private LocalDateTime createdAt;
}
