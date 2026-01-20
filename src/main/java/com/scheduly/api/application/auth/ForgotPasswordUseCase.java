package com.scheduly.api.application.auth;

import com.scheduly.api.domain.notification.Notification;
import com.scheduly.api.domain.notification.NotificationChannel;
import com.scheduly.api.domain.user.PasswordResetToken;
import com.scheduly.api.domain.user.PasswordResetTokenRepository;
import com.scheduly.api.domain.user.User;
import com.scheduly.api.domain.user.UserRepository;
import com.scheduly.api.web.dtos.ForgotPasswordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForgotPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final TemplateEngine templateEngine;
    private final com.scheduly.api.application.notification.SendNotificationUseCase sendNotificationUseCase;

    @Value("${app.reset-password.url}")
    private String resetPasswordBaseUrl;

    @Value("${app.reset-password.token-validity-hours}")
    private int tokenValidityHours;

    @Transactional
    public void execute(ForgotPasswordRequest request) {
        User user = userRepository
                .findByEmail(request.email())
                .orElse(null); // Não revelar se o email existe ou não por segurança

        if (user == null) {
            log.info("Tentativa de recuperação de senha para email não cadastrado: {}", request.email());
            // Retorna silenciosamente para não revelar se o email existe
            return;
        }

        // Limpar tokens antigos do usuário
        tokenRepository.deleteByUser(user);

        // Gerar novo token
        String token = UUID
                .randomUUID()
                .toString();
        LocalDateTime expiryDate = LocalDateTime
                .now()
                .plusHours(tokenValidityHours);

        PasswordResetToken resetToken = PasswordResetToken
                .builder()
                .token(token)
                .user(user)
                .expiryDate(expiryDate)
                .used(false)
                .createdAt(LocalDateTime.now())
                .build();

        tokenRepository.save(resetToken);

        // Gerar link de reset
        String resetLink = resetPasswordBaseUrl + "?token=" + token;

        // Preparar contexto Thymeleaf
        Context context = new Context();
        context.setVariable("userName", user.getEmail()); // Ou buscar nome do client/professional
        context.setVariable("resetLink", resetLink);
        context.setVariable("tokenValidityHours", tokenValidityHours);

        // Renderizar HTML
        String emailHtml = templateEngine.process("emails/password-reset", context);

        // Enviar e-mail
        Notification email = Notification
                .builder()
                .channel(NotificationChannel.EMAIL)
                .recipient(user.getEmail())
                .subject("Recuperação de Senha - Scheduly")
                .content(emailHtml)
                .build();

        sendNotificationUseCase.execute(email);

        log.info("Email de recuperação de senha enviado para: {}", user.getEmail());
    }
}
