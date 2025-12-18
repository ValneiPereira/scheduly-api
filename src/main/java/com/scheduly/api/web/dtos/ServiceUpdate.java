package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Dados para atualização de um serviço")
public record ServiceUpdate(
        @Schema(description = "Nome do serviço", example = "Manicure Premium") String name,

        @Schema(description = "Descrição do serviço", example = "Corte, pintura e hidratação") String description,

        @Schema(description = "Categoria do serviço", example = "BELEZA") String category,

        @Schema(description = "Subcategoria do serviço", example = "MANICURE") String subcategory,

        @Schema(description = "Duração em minutos", example = "60") @Min(value = 1, message = "Duração deve ser maior que 0") Integer durationMinutes,

        @Schema(description = "Preço em centavos", example = "5000") @Min(value = 0, message = "Preço não pode ser negativo") Integer priceCents) {
}
