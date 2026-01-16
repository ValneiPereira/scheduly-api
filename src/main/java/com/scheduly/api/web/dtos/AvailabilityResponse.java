package com.scheduly.api.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Resposta com slots de horários disponíveis para um profissional")
public record AvailabilityResponse(
        @Schema(description = "ID do profissional", example = "1")
        Long professionalId,

        @Schema(description = "Data consultada", example = "2024-04-25")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @Schema(description = "Duração em minutos considerada para os slots", example = "30")
        Integer durationMinutes,

        @Schema(description = "Lista de horários disponíveis no formato HH:mm", example = "[\"09:00\", \"09:30\", \"10:00\"]")
        List<String> availableSlots // Format: "HH:mm"
) {
}
