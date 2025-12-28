package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Dados para criação de um agendamento")
public record BookingRequest(
        @Schema(description = "ID do cliente", example = "1") @NotNull(message = "ID do cliente é obrigatório") Long clientId,
        @Schema(description = "ID do profissional", example = "1") @NotNull(message = "ID do profissional é obrigatório") Long professionalId,
        @Schema(description = "ID do serviço", example = "1") @NotNull(message = "ID do serviço é obrigatório") Long serviceId,
        @Schema(description = "Data e hora de início", example = "2025-12-25T14:00:00") @NotNull(message = "Data e hora de início são obrigatórias") LocalDateTime startAt,
        @Schema(description = "Observações extras", example = "Levar esmalte próprio") String notes) {
}
