package com.scheduly.api.infrastructure.persistence.professional;

import com.scheduly.api.domain.professional.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessionalServiceEntityMapper {

    public ProfessionalService toDomain(ProfessionalServiceEntity entity) {
        return ProfessionalService.builder()
                .professionalId(entity.getId().getProfessionalId())
                .departmentId(entity.getId().getDepartmentId())
                .priceCents(entity.getPriceCents())
                .durationMinutes(entity.getDurationMinutes())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
