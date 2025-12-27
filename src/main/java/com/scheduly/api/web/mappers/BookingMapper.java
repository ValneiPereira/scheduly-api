package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.web.dtos.BookingRequest;
import com.scheduly.api.web.dtos.BookingResponse;
import com.scheduly.api.web.dtos.BookingUpdate;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toDomain(BookingRequest request) {
        if (request == null)
            return null;

        return Booking.builder()
                .clientId(request.clientId())
                .professionalId(request.professionalId())
                .serviceId(request.serviceId())
                .startAt(request.startAt())
                .notes(request.notes())
                .build();
    }

    public Booking toDomain(BookingUpdate update) {
        if (update == null)
            return null;

        return Booking.builder()
                .startAt(update.startAt())
                .notes(update.notes())
                .status(update.status())
                .build();
    }

    public BookingResponse toResponse(Booking domain) {
        if (domain == null)
            return null;

        return new BookingResponse(
                domain.getId(),
                domain.getClientId(),
                domain.getProfessionalId(),
                domain.getServiceId(),
                domain.getStartAt(),
                domain.getEndAt(),
                domain.getStatus(),
                domain.getNotes(),
                domain.getCreatedAt());
    }
}
