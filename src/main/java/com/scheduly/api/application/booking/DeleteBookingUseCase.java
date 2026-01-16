package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBookingUseCase {

    private final BookingRepository bookingRepository;
    private final org.springframework.context.ApplicationEventPublisher eventPublisher;

    public void execute(Long id) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        var now = java.time.LocalDateTime.now();
        if (!booking.getStartAt().isAfter(now.plusHours(1))) {
            throw new IllegalArgumentException("Cancelamento permitido somente até 1 hora antes do horário");
        }

        // Publish Event
        eventPublisher.publishEvent(new com.scheduly.api.domain.booking.events.BookingCancelledEvent(this, booking));

        bookingRepository.deleteById(id);
    }
}
