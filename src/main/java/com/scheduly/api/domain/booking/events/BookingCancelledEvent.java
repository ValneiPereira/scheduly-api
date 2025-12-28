package com.scheduly.api.domain.booking.events;

import com.scheduly.api.domain.booking.Booking;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BookingCancelledEvent extends ApplicationEvent {
    private final Booking booking;

    public BookingCancelledEvent(Object source, Booking booking) {
        super(source);
        this.booking = booking;
    }
}
