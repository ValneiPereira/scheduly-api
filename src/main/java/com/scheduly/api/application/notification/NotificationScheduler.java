package com.scheduly.api.application.notification;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingFilter;
import com.scheduly.api.domain.booking.BookingRepository;
import com.scheduly.api.domain.notification.Notification;
import com.scheduly.api.domain.notification.NotificationChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final BookingRepository bookingRepository;
    private final SendNotificationUseCase sendNotificationUseCase;
    private final com.scheduly.api.domain.client.ClientRepository clientRepository;
    private final com.scheduly.api.domain.professional.ProfessionalRepository professionalRepository;
    private final com.scheduly.api.domain.service.ServiceRepository serviceRepository;
    private final org.thymeleaf.TemplateEngine templateEngine;

    // Roda todo dia às 08:00
    @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyReminders() {
        log.info("Iniciando envio de lembretes diários...");

        java.time.LocalDate tomorrow = java.time.LocalDate.now().plusDays(1);

        // Simplificado: Buscar todos e filtrar (Ideal seria uma query no banco)
        List<Booking> tomorrowBookings = bookingRepository.findAll(new BookingFilter())
                .stream()
                .filter(b -> b.getStartAt().toLocalDate().equals(tomorrow))
                .toList();

        for (Booking booking : tomorrowBookings) {
            sendReminder(booking);
        }
    }

    private void sendReminder(Booking booking) {
        log.info("Enviando lembrete para agendamento: {}", booking.getId());

        try {
            var client = clientRepository.findById(booking.getClientId()).orElseThrow();
            var professional = professionalRepository.findById(booking.getProfessionalId()).orElseThrow();
            var service = serviceRepository.findById(booking.getServiceId()).orElseThrow();

            var timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Preparar contexto Thymeleaf
            var context = new org.thymeleaf.context.Context();
            context.setVariable("clientName", client.getName());
            context.setVariable("professionalName", professional.getName());
            context.setVariable("serviceName", service.getName());
            context.setVariable("time", booking.getStartAt().format(timeFormatter));

            // Renderizar HTML
            String emailHtml = templateEngine.process("emails/booking-reminder", context);

            Notification email = Notification.builder()
                    .bookingId(booking.getId())
                    .channel(NotificationChannel.EMAIL)
                    .recipient(client.getEmail())
                    .subject("Lembrete de Agendamento - Scheduly")
                    .content(emailHtml)
                    .build();

            sendNotificationUseCase.execute(email);

        } catch (Exception e) {
            log.error("Erro ao processar lembrete diário para agendamento {}: {}", booking.getId(), e.getMessage());
        }
    }
}
