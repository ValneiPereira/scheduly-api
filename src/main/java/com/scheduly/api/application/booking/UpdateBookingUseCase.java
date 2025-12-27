package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingRepository;
import com.scheduly.api.domain.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateBookingUseCase {

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;

    public Booking execute(Long id, Booking updateData) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        if (updateData.getStatus() != null) {
            booking.setStatus(updateData.getStatus());
        }

        if (updateData.getNotes() != null) {
            booking.setNotes(updateData.getNotes());
        }

        if (updateData.getStartAt() != null) {
            // Re-calculate end time if start time changed
            var service = serviceRepository.findById(booking.getServiceId())
                    .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

            booking.setStartAt(updateData.getStartAt());
            booking.setEndAt(booking.getStartAt().plusMinutes(service.getDuration()));

            // Validate conflicts for new time
            validateConflicts(booking);
        }

        return bookingRepository.save(booking);
    }

    private void validateConflicts(Booking booking) {
        var conflicts = bookingRepository.findOverlapping(
                booking.getProfessionalId(),
                booking.getStartAt(),
                booking.getEndAt());

        // Filter out current booking from conflicts
        long conflictCount = conflicts.stream()
                .filter(c -> !c.getId().equals(booking.getId()))
                .count();

        if (conflictCount > 0) {
            throw new IllegalStateException("O profissional já possui um agendamento neste horário");
        }
    }
}
