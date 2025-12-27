package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBookingUseCase {

    private final BookingRepository bookingRepository;

    public Booking execute(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
    }
}
