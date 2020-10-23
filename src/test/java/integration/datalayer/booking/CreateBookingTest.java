package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateBookingTest {
    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;
    private Time start = new Time(12,0,0);
    private Time end = new Time(12,30,0);
    private Date date = new Date(System.currentTimeMillis());

    @BeforeAll
    public void Setup() throws SQLException {
        var url = "jdbc:mysql://localhost:3307/";
        var db = "DemoApplicationTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("3")
                .dataSource(url, "root", "testuser123"));

        flyway.migrate();

        bookingStorage = new BookingStorageImpl(url+db, "root", "testuser123");
        customerStorage = new CustomerStorageImpl(url+db, "root", "testuser123");
        employeeStorage = new EmployeeStorageImpl(url+db, "root", "testuser123");

        var numBookings = bookingStorage.getBookings().size();
        if (numBookings < 100) {
            addFakeBooking(100 - numBookings);
        }
    }

    private void addFakeBooking(int numBookings) throws SQLException {
        Faker faker = new Faker();
        for (int i = 1; i < numBookings; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName());
            customerStorage.createCustomer(c);
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), date);
            employeeStorage.createEmployee(e);
            BookingCreation b = new BookingCreation(i, i, new Date(System.currentTimeMillis()), start, end);
            bookingStorage.createBooking(b);
        }

    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        bookingStorage.createBooking(new BookingCreation(1,1, date,start, end));

        // Assert
        var bookings = bookingStorage.getBookings();
        assertTrue(
                bookings.stream().anyMatch(x ->
                        x.getCustomerId() == 1 &&
                                x.getEmployeeId() == 1));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = bookingStorage.createBooking(new BookingCreation(1, 1, date, start, end));
        var id2 = bookingStorage.createBooking(new BookingCreation(1, 1, date, start, end));

        // Assert
        assertEquals(1, id2 - id1);
    }
}
