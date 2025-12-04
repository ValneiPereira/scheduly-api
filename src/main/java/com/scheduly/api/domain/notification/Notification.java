package com.scheduly.api.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private Long id;

    // Destinatário
    private Long recipientId; // ID do destinatário (cliente ou profissional)
    private RecipientType recipientType; // Tipo do destinatário

    // Conteúdo
    private NotificationType type; // Tipo de notificação
    private String title; // Título da notificação
    private String message; // Mensagem

    // Relacionamento
    private Long bookingId; // ID do agendamento relacionado

    // Controle
    private Boolean read; // Se foi lida
    private Boolean sent; // Se foi enviada
    private LocalDateTime sentAt; // Data/hora de envio
    private LocalDateTime readAt; // Data/hora de leitura
    private LocalDateTime createdAt; // Data de criação
}
