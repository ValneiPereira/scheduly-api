package com.scheduly.api.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record ProfessionalResponse(
        Long id,
        String name,
        String phone,
        AddressResponse address,
        String bio,
        List<Long> specialtyIds,
        BigDecimal rating,
        Integer totalReviews,

        @JsonFormat(pattern = "HH:mm")
        @Schema(type = "string", example = "09:00")
        LocalTime workStartTime,

        @JsonFormat(pattern = "HH:mm")
        @Schema(type = "string", example = "19:00")
        LocalTime workEndTime,
        List<String> workingDays,
        Boolean active,
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDateTime createdAt,
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDateTime updatedAt) {
 }
