package com.scheduly.api.domain.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Long id;
    private Long clientId;
    private Long professionalId;
    private Long serviceId;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private BookingStatus status;
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
