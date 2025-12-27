package com.scheduly.api.domain.booking;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class BookingFilter {
    private Long clientId;
    private Long professionalId;
    private LocalDate date;
}
