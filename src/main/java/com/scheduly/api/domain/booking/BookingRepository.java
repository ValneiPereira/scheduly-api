package com.scheduly.api.domain.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);

    Optional<Booking> findById(Long id);

    List<Booking> findAll(BookingFilter filter);

    void deleteById(Long id);

    List<Booking> findOverlapping(Long professionalId, LocalDateTime start, LocalDateTime end);
}
