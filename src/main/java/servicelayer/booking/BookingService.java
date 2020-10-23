package servicelayer.booking;

import dto.Booking;
import dto.BookingCreation;

public interface BookingService {
    int createBooking(BookingCreation booking) throws BookingServiceException;
}
