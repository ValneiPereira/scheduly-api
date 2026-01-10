package com.scheduly.api.web.dtos;

import com.scheduly.api.domain.booking.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta com dados do agendamento")
public record BookingResponse(
                @Schema(description = "ID único do agendamento", example = "1") Long id,

                @Schema(description = "ID do cliente", example = "1") Long clientId,

                @Schema(description = "ID do profissional", example = "1") Long professionalId,

                @Schema(description = "ID do departamento (serviceId mantido por compatibilidade)", example = "1") Long serviceId,

                @Schema(description = "Data e hora de início", example = "2025-12-25T14:00:00") LocalDateTime startAt,

                @Schema(description = "Data e hora de término", example = "2025-12-25T15:00:00") LocalDateTime endAt,

                @Schema(description = "Status atual", example = "CONFIRMED") BookingStatus status,
                @Schema(description = "ID do endereço do serviço", example = "1") Long addressId,
                @Schema(description = "Observações", example = "Levar esmalte próprio") String notes,

                @Schema(description = "Data de criação", example = "2025-12-19T10:00:00") LocalDateTime createdAt) {
}
