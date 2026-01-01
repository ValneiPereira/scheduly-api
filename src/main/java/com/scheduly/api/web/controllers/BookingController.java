package com.scheduly.api.web.controllers;

import com.scheduly.api.BookingsApi;
import com.scheduly.api.application.booking.*;
import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingFilter;
import com.scheduly.api.web.dtos.BookingRequest;
import com.scheduly.api.web.dtos.BookingResponse;
import com.scheduly.api.web.dtos.BookingUpdate;
import com.scheduly.api.web.mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookingController implements BookingsApi {

    private final CreateBookingUseCase createBookingUseCase;
    private final GetBookingUseCase getBookingUseCase;
    private final ListBookingsUseCase listBookingsUseCase;
    private final UpdateBookingUseCase updateBookingUseCase;
    private final DeleteBookingUseCase deleteBookingUseCase;
    private final BookingMapper mapper;

    @Override
    public ResponseEntity<BookingResponse> createBooking(BookingRequest request) {
        Booking domain = mapper.toDomain(request);
        Booking created = createBookingUseCase.execute(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @Override
    public ResponseEntity<BookingResponse> getBooking(Long bookingId) {
        Booking booking = getBookingUseCase.execute(bookingId);
        return ResponseEntity.ok(mapper.toResponse(booking));
    }

    @Override
    public ResponseEntity<List<BookingResponse>> listBookings(Long clientId, Long professionalId, LocalDate date) {
        BookingFilter filter = BookingFilter.builder()
                .clientId(clientId)
                .professionalId(professionalId)
                .date(date)
                .build();

        List<BookingResponse> response = listBookingsUseCase.execute(filter).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BookingResponse> updateBooking(Long bookingId, BookingUpdate request) {
        Booking domainUpdate = mapper.toDomain(request);
        Booking updated = updateBookingUseCase.execute(bookingId, domainUpdate);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Override
    public ResponseEntity<Void> cancelBooking(Long bookingId) {
        deleteBookingUseCase.execute(bookingId);
        return ResponseEntity.noContent().build();
    }
}
