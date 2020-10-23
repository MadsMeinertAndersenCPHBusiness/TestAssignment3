package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.BookingCreation;
import dto.CustomerCreation;
import dto.SmsMessage;
import servicelayer.customer.CustomerServiceException;
import servicelayer.notifications.SmsService;

import java.sql.SQLException;

public class BookingServiceImpl implements BookingService {
    private SmsService smsService;
    private BookingStorage bookingStorage;

    public BookingServiceImpl(BookingStorage bookingStorage, SmsService smsService) {
        this.bookingStorage = bookingStorage;
        this.smsService = smsService;
    }

    @Override
    public int createBooking(BookingCreation booking) throws BookingServiceException {
        if (null != booking) {
            try {
                smsService.sendSms(new SmsMessage("test", "test"));
                return bookingStorage.createBooking(booking);
            } catch (SQLException throwables) {
                throw new BookingServiceException(throwables.getMessage());
            }
        } throw new BookingServiceException("Invalid");
    }
}
