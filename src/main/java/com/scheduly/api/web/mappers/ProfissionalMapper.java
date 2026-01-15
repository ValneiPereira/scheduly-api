package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.department.DepartmentRepository;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.web.dtos.ProfessionalRequest;
import com.scheduly.api.web.dtos.ProfessionalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfissionalMapper {

    private final AddressMapper addressMapper;
    private final DepartmentRepository departmentRepository;

    public Professional toDomain(ProfessionalRequest request) {
        if (request == null)
            return null;

        return Professional.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .cpf(request.cpf())
                .address(addressMapper.toDomain(request.address()))
                .bio(request.bio())
                .specialtyIds(request.specialtyIds())
                .workStartTime(request.workStartTime())
                .workEndTime(request.workEndTime())
                .intervalMinutes(request.intervalMinutes())
                .workingDays(request.workingDays())
                .active(request.active())
                .build();
    }

    public ProfessionalResponse toResponse(Professional professional) {
        if (professional == null)
            return null;

        String specialization = calculateSpecialization(professional.getSpecialtyIds());

        return new ProfessionalResponse(
                professional.getId(),
                professional.getName(),
                professional.getEmail(),
                professional.getPhone(),
                professional.getCpf(),
                addressMapper.toResponse(professional.getAddress()),
                professional.getBio(),
                professional.getSpecialtyIds(),
                professional.getRating(),
                professional.getTotalReviews(),
                specialization,
                professional.getWorkStartTime(),
                professional.getWorkEndTime(),
                professional.getIntervalMinutes(),
                professional.getWorkingDays(),
                professional.getActive(),
                professional.getCreatedAt(),
                professional.getUpdatedAt());
    }

    /**
     * Calcula a specialization baseada nos specialtyIds do profissional.
     * Se houver múltiplos, retorna o primeiro. Se nenhum, retorna null.
     */
    private String calculateSpecialization(List<Long> specialtyIds) {
        if (specialtyIds == null || specialtyIds.isEmpty()) {
            return null;
        }

        // Busca o primeiro specialtyId (primeira especialização)
        Long firstSpecialtyId = specialtyIds.get(0);
        return departmentRepository.findById(firstSpecialtyId)
                .map(department -> "Especialista em " + department.getName())
                .orElse(null);
    }
}
