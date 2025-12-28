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
    private Long bookingId;
    private NotificationChannel channel;
    private String recipient;
    private String subject;
    private String content;
    private NotificationStatus status; // PENDING, SENT, FAILED
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
}
