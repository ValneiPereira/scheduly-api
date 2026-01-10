package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para criação de um novo departamento")
public record DepartmentRequest(
    @Schema(description = "Nome do departamento", example = "Manicure Básica") @NotBlank(message = "Nome é obrigatório")
    String name,
    @Schema(description = "Descrição do departamento", example = "Corte e pintura básica")
    String description,
    @Schema(description = "Categoria do departamento", example = "BELEZA") @NotBlank(message = "Categoria é obrigatória")
    String category,
    @Schema(description = "Subcategoria do departamento", example = "MANICURE") @NotBlank(message = "Subcategoria é obrigatória")
    String subcategory,
    @Schema(description = "Duração em minutos", example = "45") @NotNull(message = "Duração é obrigatória") @Min(value = 1, message = "Duração deve ser maior que 0")
    Integer durationMinutes,
    @Schema(description = "Preço em centavos", example = "3500") @NotNull(message = "Preço é obrigatório") @Min(value = 0, message = "Preço não pode ser negativo")
    Integer priceCents) {
}
