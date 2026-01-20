package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para atualizar serviço do profissional")
public record ProfessionalServiceUpdate(
        @Schema(description = "Preço em centavos", example = "6000")
        @NotNull(message = "Preço é obrigatório")
        Integer priceCents,

        @Schema(description = "Duração em minutos (opcional - se não informado, usa a duração padrão do serviço)", example = "45")
        Integer durationMinutes
) {
}
