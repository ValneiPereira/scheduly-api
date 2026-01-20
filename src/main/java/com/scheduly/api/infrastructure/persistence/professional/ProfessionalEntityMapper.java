package com.scheduly.api.infrastructure.persistence.professional;

import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.infrastructure.persistence.address.AddressEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ProfessionalEntityMapper {

    private final AddressEntityMapper addressMapper;

    public Professional toDomain(ProfessionalEntity entity) {
        if (entity == null) {
            return null;
        }
        return Professional.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(addressMapper.toDomain(entity.getAddress()))
                .avatarUrl(entity.getAvatarUrl())
                .bio(entity.getBio())
                .specialtyIds(entity.getSpecialtyIds() != null ? entity.getSpecialtyIds() : Collections.emptyList())
                .rating(entity.getRating())
                .totalReviews(entity.getTotalReviews())
                .workStartTime(entity.getWorkStartTime())
                .workEndTime(entity.getWorkEndTime())
                .intervalMinutes(entity.getIntervalMinutes())
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
                .phone(professional.getPhone())
                .address(addressMapper.toEntity(professional.getAddress()))
                .avatarUrl(professional.getAvatarUrl())
                .bio(professional.getBio())
                .specialtyIds(professional.getSpecialtyIds())
                .rating(professional.getRating())
                .totalReviews(professional.getTotalReviews())
                .workStartTime(professional.getWorkStartTime())
                .workEndTime(professional.getWorkEndTime())
                .intervalMinutes(professional.getIntervalMinutes())
                .workingDays(professional.getWorkingDays())
                .active(professional.getActive())
                .createdAt(professional.getCreatedAt())
                .updatedAt(professional.getUpdatedAt())
                .build();
    }

}
