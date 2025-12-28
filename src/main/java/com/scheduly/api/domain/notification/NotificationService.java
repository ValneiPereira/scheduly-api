package com.scheduly.api.domain.notification;

public interface NotificationService {
    void send(Notification notification);
    NotificationChannel getChannel();
}
