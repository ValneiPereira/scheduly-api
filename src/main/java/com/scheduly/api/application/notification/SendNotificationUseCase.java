package com.scheduly.api.application.notification;

import com.scheduly.api.domain.notification.Notification;
import com.scheduly.api.domain.notification.NotificationChannel;
import com.scheduly.api.domain.notification.NotificationRepository;
import com.scheduly.api.domain.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.scheduly.api.domain.notification.NotificationStatus.PENDING;
import static com.scheduly.api.domain.notification.NotificationStatus.SENT;

@Service
@RequiredArgsConstructor
public class SendNotificationUseCase {

    private final NotificationRepository notificationRepository;
    private final List<NotificationService> services;

    public void execute(Notification notification) {
        NotificationService service = findService(notification.getChannel());

        // 1. Mark as PENDING originally
        notification.setStatus(PENDING);

        // 2. Save history
        Notification saved = notificationRepository.save(notification);

        // 3. Send
        service.send(saved);

        // 4. Update status and sent time
        if (SENT.equals(saved.getStatus())) {
            saved.setSentAt(LocalDateTime.now());
        }

        notificationRepository.save(saved);
    }

    private NotificationService findService(NotificationChannel channel) {
        return services.stream()
                .filter(s -> s.getChannel() == channel)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Serviço de notificação não encontrado para o canal: " + channel));
    }
}
