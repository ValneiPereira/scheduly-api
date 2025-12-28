package com.scheduly.api.infrastructure.notifications;

import com.scheduly.api.domain.notification.Notification;
import com.scheduly.api.domain.notification.NotificationChannel;
import com.scheduly.api.domain.notification.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static com.scheduly.api.domain.notification.NotificationChannel.EMAIL;
import static com.scheduly.api.domain.notification.NotificationStatus.FAILED;
import static com.scheduly.api.domain.notification.NotificationStatus.SENT;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService implements NotificationService {

    public static final String UTF_8 = "UTF-8";
    private final JavaMailSender mailSender;

    @Override
    public void send(Notification notification) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8);

            helper.setTo(notification.getRecipient());
            helper.setSubject(notification.getSubject());
            helper.setText(notification.getContent(), true); // true = HTML

            mailSender.send(message);

            notification.setStatus(SENT);
            log.info("[EMAIL] Mensagem enviada com sucesso para {}", notification.getRecipient());

        } catch (MessagingException e) {
            notification.setStatus(FAILED);
            log.error("[EMAIL] Erro ao enviar e-mail para {}: {}", notification.getRecipient(), e.getMessage());
        }
    }

    @Override
    public NotificationChannel getChannel() {
        return EMAIL;
    }
}
