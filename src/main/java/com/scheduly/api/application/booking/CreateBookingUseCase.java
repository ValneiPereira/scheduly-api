package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingRepository;
import com.scheduly.api.domain.booking.BookingStatus;
import com.scheduly.api.domain.booking.events.BookingCreatedEvent;
import com.scheduly.api.domain.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateBookingUseCase {

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Booking execute(Booking booking) {
        // 1. Get service to calculate duration
        var service = serviceRepository.findById(booking.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        // 2. Set calculated end time
        booking.setEndAt(booking.getStartAt().plusMinutes(service.getDuration()));

        // 3. Set initial status
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }

        // 4. Validate domain constraints
        validateBooking(booking);

        // 5. Save
        Booking saved = bookingRepository.save(booking);

        // 6. Publish Event
        eventPublisher.publishEvent(new BookingCreatedEvent(this, saved));

        return saved;
    }

    private void validateBooking(Booking booking) {
        if (booking.getStartAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data de agendamento não pode ser no passado");
        }

        // Check for conflicts
        var conflicts = bookingRepository.findOverlapping(
                booking.getProfessionalId(),
                booking.getStartAt(),
                booking.getEndAt());

        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("O profissional já possui um agendamento neste horário");
        }
    }
}
