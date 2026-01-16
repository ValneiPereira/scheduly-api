package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.department.DepartmentRepository;
import com.scheduly.api.domain.professional.ProfessionalService;
import com.scheduly.api.web.dtos.ProfessionalServiceRequest;
import com.scheduly.api.web.dtos.ProfessionalServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessionalServiceMapper {
    private final DepartmentRepository departmentRepository;

    public ProfessionalService toDomain(Long professionalId, ProfessionalServiceRequest request) {
        return ProfessionalService.builder()
                .professionalId(professionalId)
                .departmentId(request.departmentId())
                .priceCents(request.priceCents())
                .durationMinutes(request.durationMinutes())
                .build();
    }

    public ProfessionalServiceResponse toResponse(ProfessionalService professionalService) {
        var department = departmentRepository.findById(professionalService.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        return new ProfessionalServiceResponse(
                professionalService.getDepartmentId(),
                department.getName(),
                department.getCategory().name(),
                department.getSubcategory().name(),
                professionalService.getPriceCents(),
                professionalService.getDurationMinutes()
        );
    }
}
