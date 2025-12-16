package com.scheduly.api.infrastructure.persistence.professional;

import com.scheduly.api.domain.common.Address;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.infrastructure.persistence.common.AddressEmbeddable;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ProfessionalEntityMapper {

    public Professional toDomain(ProfessionalEntity entity) {
        if (entity == null) {
            return null;
        }
        return Professional.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .cpf(entity.getCpf())
                .phone(entity.getPhone())
                .address(toAddressDomain(entity.getAddress()))
                .bio(entity.getBio())
                .specialtyIds(entity.getSpecialtyIds() != null ? entity.getSpecialtyIds() : Collections.emptyList())
                .rating(entity.getRating())
                .totalReviews(entity.getTotalReviews())
                .workStartTime(entity.getWorkStartTime())
                .workEndTime(entity.getWorkEndTime())
                .workingDays(entity.getWorkingDays() != null ? entity.getWorkingDays() : Collections.emptyList())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ProfessionalEntity toEntity(Professional professional) {
        if (professional == null) {
            return null;
        }
        return ProfessionalEntity.builder()
                .id(professional.getId())
                .name(professional.getName())
                .email(professional.getEmail())
                .cpf(professional.getCpf())
                .phone(professional.getPhone())
                .address(toAddressEmbeddable(professional.getAddress()))
                .bio(professional.getBio())
                .specialtyIds(professional.getSpecialtyIds())
                .rating(professional.getRating())
                .totalReviews(professional.getTotalReviews())
                .workStartTime(professional.getWorkStartTime())
                .workEndTime(professional.getWorkEndTime())
                .workingDays(professional.getWorkingDays())
                .active(professional.getActive())
                .createdAt(professional.getCreatedAt())
                .updatedAt(professional.getUpdatedAt())
                .build();
    }

    private Address toAddressDomain(AddressEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }

        return Address.builder()
                .street(embeddable.getStreet())
                .number(embeddable.getNumber())
                .complement(embeddable.getComplement())
                .neighborhood(embeddable.getNeighborhood())
                .city(embeddable.getCity())
                .state(embeddable.getState())
                .zipCode(embeddable.getZipCode())
                .build();
    }

    private AddressEmbeddable toAddressEmbeddable(Address address) {
        if (address == null) {
            return null;
        }
        return AddressEmbeddable.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }
}
