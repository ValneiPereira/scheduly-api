package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.web.dtos.ProfessionalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfissionalMapper {

    private final AddressMapper addressMapper;

    public Professional toDomain(ProfessionalRequest request) {
        if (request == null) return null;

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
                .build();

    }
}
