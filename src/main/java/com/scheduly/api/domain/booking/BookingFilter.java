package com.scheduly.api.domain.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingFilter {
    private Long clientId;
    private Long professionalId;
    private LocalDate date;
}
