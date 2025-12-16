package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.web.dtos.ProfessionalRequest;
import com.scheduly.api.web.dtos.ProfessionalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfissionalMapper {

    private final AddressMapper addressMapper;

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
                .workingDays(request.workingDays())
                .active(request.active())
                .build();
    }

    public ProfessionalResponse toResponse(Professional professional) {
        if (professional == null)
            return null;

        return new ProfessionalResponse(
                professional.getId(),
                professional.getName(),
                professional.getPhone(),
                addressMapper.toResponse(professional.getAddress()),
                professional.getBio(),
                professional.getSpecialtyIds(),
                professional.getRating(),
                professional.getTotalReviews(),
                professional.getWorkStartTime(),
                professional.getWorkEndTime(),
                professional.getWorkingDays(),
                professional.getActive(),
                professional.getCreatedAt(),
                professional.getUpdatedAt());
    }
}
