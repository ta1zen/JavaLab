package lab3;

import lombok.Getter;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a booking made by a customer for a hotel room, including details about the
 * room, customer, booking period, and payment status.
 */
@Getter
//@Builder
public class Booking {
    private Room room;
    private final Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isPaid;

    public Booking(Room room, Customer customer, LocalDate startDate, LocalDate endDate, boolean isPaid) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "room=" + room +
                ", customer=" + customer +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", isPaid=" + isPaid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return room.equals(booking.room) &&
                customer.equals(booking.customer) &&
                startDate.equals(booking.startDate) &&
                endDate.equals(booking.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, startDate, endDate);
    }
}

