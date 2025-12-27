package com.scheduly.api.web.dtos;

import com.scheduly.api.domain.booking.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Dados para atualização de um agendamento")
public record BookingUpdate(
        @Schema(description = "Nova data e hora de início", example = "2025-12-26T10:00:00") LocalDateTime startAt,

        @Schema(description = "Novas observações", example = "Alterado para francesinha") String notes,

        @Schema(description = "Novo status", example = "CANCELLED") BookingStatus status) {
}
