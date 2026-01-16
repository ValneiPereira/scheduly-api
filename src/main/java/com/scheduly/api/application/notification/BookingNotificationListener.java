package com.scheduly.api.application.notification;

import com.scheduly.api.domain.booking.events.BookingCreatedEvent;
import com.scheduly.api.domain.booking.events.BookingCancelledEvent;
import com.scheduly.api.domain.notification.Notification;
import com.scheduly.api.domain.notification.NotificationChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingNotificationListener {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final com.scheduly.api.domain.client.ClientRepository clientRepository;
    private final com.scheduly.api.domain.professional.ProfessionalRepository professionalRepository;
    private final com.scheduly.api.domain.department.DepartmentRepository departmentRepository;
    private final org.thymeleaf.TemplateEngine templateEngine;

    @Async
    @EventListener
    public void handleBookingCreated(BookingCreatedEvent event) {
        var booking = event.getBooking();
        log.info("Processando notificações para novo agendamento: {}", booking.getId());

        try {
            var client = clientRepository.findById(booking.getClientId()).orElseThrow();
            var professional = professionalRepository.findById(booking.getProfessionalId()).orElseThrow();
            var department = departmentRepository.findById(booking.getDepartmentId()).orElseThrow();

            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter
                    .ofPattern("dd/MM/yyyy");
            java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");

            // Preparar contexto Thymeleaf
            org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
            context.setVariable("clientName", client.getName());
            context.setVariable("professionalName", professional.getName());
            context.setVariable("serviceName", department.getName());
            context.setVariable("date", booking.getStartAt().format(dateFormatter));
            context.setVariable("time", booking.getStartAt().format(timeFormatter));

            // Renderizar HTML
            String emailHtml = templateEngine.process("emails/booking-confirmed", context);

            // 1. Enviar E-mail
            Notification email = Notification.builder()
                    .bookingId(booking.getId())
                    .channel(NotificationChannel.EMAIL)
                    .recipient(client.getEmail())
                    .subject("Agendamento Confirmado - Scheduly")
                    .content(emailHtml)
                    .build();

            sendNotificationUseCase.execute(email);

            // 2. Enviar WhatsApp (Mock)
            Notification whatsapp = Notification.builder()
                    .bookingId(booking.getId())
                    .channel(NotificationChannel.WHATSAPP)
                    .recipient(client.getPhone())
                    .content("Olá " + client.getName() + ", seu agendamento de " + department.getName()
                            + " foi confirmado para " + booking.getStartAt().format(dateFormatter) + " às "
                            + booking.getStartAt().format(timeFormatter))
                    .build();

            sendNotificationUseCase.execute(whatsapp);

        } catch (Exception e) {
            log.error("Erro ao processar notificações de criação: {}", e.getMessage());
        }
    }

    @Async
    @EventListener
    public void handleBookingCancelled(BookingCancelledEvent event) {
        var booking = event.getBooking();
        log.info("Processando notificações para cancelamento: {}", booking.getId());

        try {
            var client = clientRepository.findById(booking.getClientId()).orElseThrow();
            var department = departmentRepository.findById(booking.getDepartmentId()).orElseThrow();

            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter
                    .ofPattern("dd/MM/yyyy");

            // Preparar contexto Thymeleaf
            org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
            context.setVariable("clientName", client.getName());
            context.setVariable("serviceName", department.getName());
            context.setVariable("date", booking.getStartAt().format(dateFormatter));

            // Renderizar HTML
            String emailHtml = templateEngine.process("emails/booking-cancelled", context);

            // Enviar E-mail
            Notification email = Notification.builder()
                    .bookingId(booking.getId())
                    .channel(NotificationChannel.EMAIL)
                    .recipient(client.getEmail())
                    .subject("Agendamento Cancelado - Scheduly")
                    .content(emailHtml)
                    .build();

            sendNotificationUseCase.execute(email);

        } catch (Exception e) {
            log.error("Erro ao processar notificações de cancelamento: {}", e.getMessage());
        }
    }
}
