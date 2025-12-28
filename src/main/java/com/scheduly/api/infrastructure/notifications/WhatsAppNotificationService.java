package com.scheduly.api.infrastructure.notifications;

import com.scheduly.api.domain.notification.Notification;
import com.scheduly.api.domain.notification.NotificationChannel;
import com.scheduly.api.domain.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.scheduly.api.domain.notification.NotificationChannel.WHATSAPP;
import static com.scheduly.api.domain.notification.NotificationStatus.*;

@Slf4j
@Service
public class WhatsAppNotificationService implements NotificationService {

    @Override
    public void send(Notification notification) {
        log.info("[WHATSAPP MOCK] Enviando mensagem para {}: {}",
                notification.getRecipient(),
                notification.getContent());

        notification.setStatus(SENT);
    }

    @Override
    public NotificationChannel getChannel() {
        return WHATSAPP;
    }
}
