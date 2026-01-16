package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Serviço cadastrado pelo profissional")
public record ProfessionalServiceResponse(
        Long departmentId,
        String name,
        String category,
        String subcategory,
        Integer priceCents,
        Integer durationMinutes
) {
}
