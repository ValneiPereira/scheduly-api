package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBookingUseCase {

    private final BookingRepository bookingRepository;

    public void execute(Long id) {
        if (!bookingRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Agendamento não encontrado");
        }
        bookingRepository.deleteById(id);
    }
}
