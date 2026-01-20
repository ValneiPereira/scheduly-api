package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para cadastrar serviço do profissional")
public record ProfessionalServiceRequest(
        @Schema(description = "ID do serviço (departamento)", example = "1")
        @NotNull(message = "ID do serviço é obrigatório")
        Long departmentId,

        @Schema(description = "Preço em centavos", example = "5000")
        @NotNull(message = "Preço é obrigatório")
        Integer priceCents,

        @Schema(description = "Duração em minutos (opcional - se não informado, usa a duração padrão do serviço)", example = "60")
        Integer durationMinutes
) {
}
