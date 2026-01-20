package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.department.DepartmentRepository;
import com.scheduly.api.domain.professional.ProfessionalService;
import com.scheduly.api.web.dtos.ProfessionalServiceRequest;
import com.scheduly.api.web.dtos.ProfessionalServiceResponse;
import com.scheduly.api.web.dtos.ProfessionalServiceUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessionalServiceMapper {
    private final DepartmentRepository departmentRepository;

    public ProfessionalService toDomain(Long professionalId, ProfessionalServiceRequest request) {
        // Se durationMinutes não for informado, busca a duração padrão do Department
        Integer durationMinutes = request.durationMinutes();
        if (durationMinutes == null) {
            var department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
            durationMinutes = department.getDuration();
        }

        return ProfessionalService.builder()
                .professionalId(professionalId)
                .departmentId(request.departmentId())
                .priceCents(request.priceCents())
                .durationMinutes(durationMinutes)
                .build();
    }

    public ProfessionalService toDomain(ProfessionalServiceUpdate update) {
        return ProfessionalService.builder()
                .priceCents(update.priceCents())
                .durationMinutes(update.durationMinutes())
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
