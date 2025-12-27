package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingFilter;
import com.scheduly.api.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListBookingsUseCase {

    private final BookingRepository bookingRepository;

    public List<Booking> execute(BookingFilter filter) {
        return bookingRepository.findAll(filter);
    }
}
