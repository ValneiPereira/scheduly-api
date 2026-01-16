package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "Request para consulta de disponibilidade de um profissional")
public record AvailabilityRequest(
        @Schema(description = "ID do profissional", example = "1")
        @NotNull(message = "ID do profissional é obrigatório")
        Long professionalId,

        @Schema(description = "Data para consulta", example = "2024-04-25")
        @NotNull(message = "Data é obrigatória")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date,

        @Schema(description = "Duração em minutos do serviço", example = "30")
        @Min(value = 15, message = "Duração mínima é 15 minutos")
        @Max(value = 480, message = "Duração máxima é 480 minutos (8 horas)")
        Integer durationMinutes // 15min a 8h
) {
    public Integer getDurationMinutesOrDefault() {
        return durationMinutes != null ? durationMinutes : 30;
    }
}
