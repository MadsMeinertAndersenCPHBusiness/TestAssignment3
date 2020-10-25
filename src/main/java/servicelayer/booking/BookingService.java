package servicelayer.booking;

import dto.Booking;
import dto.BookingCreation;

import java.sql.SQLException;
import java.util.Collection;

public interface BookingService {
    int createBooking(BookingCreation booking) throws BookingServiceException;
    Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException;
    Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException;
}
