package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.Booking;
import dto.BookingCreation;
import dto.SmsMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.notifications.SmsService;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateBookingTest {
    // SUT (System Under Test)
    private BookingService bookingService;

    // DOC (Depended-on Component)
    private BookingStorage storageMock;
    private SmsService smsServiceMock;

    @BeforeAll
    public void beforeAll(){
        storageMock = mock(BookingStorage.class);
        smsServiceMock = mock(SmsService.class);
        bookingService = new BookingServiceImpl(storageMock, smsServiceMock);
    }
    @Test
    public void mustCallSmsServiceWhenCreatingBooking() throws BookingServiceException, SQLException {
        // Arrange
        // Act
        var customerId = 1;
        var employeeId = 1;
        var date = new Date(System.currentTimeMillis());
        var start = new Time(12,30,00);
        var end = new Time(12, 30, 00);
        BookingCreation booking = new BookingCreation(customerId, employeeId, date, start, end);
        bookingService.createBooking(booking);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(smsServiceMock, times(1)).sendSms(new SmsMessage("test", "test"));
    }
    @Test
    public void mustThrowExceptionWhenArgumentsAreDifferent() throws BookingServiceException, SQLException {
        //Arrange
        //Act
        Exception thrown = assertThrows(
                BookingServiceException.class, () -> bookingService.createBooking(null)
        );
        assertTrue(thrown.getMessage().contains("Invalid"));
        //Assert
    }
}
