package com.scheduly.api.domain.booking;

import com.scheduly.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Long id;

    // Relacionamentos
    private Long clientId; // ID do cliente
    private Long professionalId; // ID do profissional
    private List<Long> serviceIds; // IDs dos serviços agendados

    // Data e horário
    private LocalDate scheduledDate; // Data do agendamento
    private LocalTime scheduledTime; // Horário de início
    private LocalTime endTime; // Horário de término (calculado)

    // Valores
    private Integer totalDuration; // Duração total em minutos (soma dos serviços)
    private BigDecimal totalPrice; // Preço total (soma dos serviços)
    private BigDecimal discount; // Desconto aplicado
    private BigDecimal finalPrice; // Preço final após desconto

    // Status e controle
    private BookingStatus status; // Status do agendamento
    private String notes; // Observações do cliente
    private String professionalNotes; // Observações do profissional
    private String cancellationReason; // Motivo do cancelamento (se aplicável)

    // Timestamps
    private LocalDateTime createdAt; // Data de criação
    private LocalDateTime updatedAt; // Data de atualização
    private LocalDateTime confirmedAt; // Data de confirmação
    private LocalDateTime cancelledAt; // Data de cancelamento
    private LocalDateTime completedAt; // Data de conclusão
}
