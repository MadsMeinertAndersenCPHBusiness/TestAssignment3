package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookingStorageImpl implements BookingStorage {

    private String connectionString;
    private String username, password;
    private List<Booking> bookingsForCustomer = new ArrayList<>();
    private List<Booking> bookingsForEmployee = new ArrayList<>();

    public BookingStorageImpl(String conStr, String user, String pass){
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }


    @Override
    public int createBooking(BookingCreation bookingToCreate) throws SQLException {
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bookingToCreate.getCustomerId());
            stmt.setInt(2, bookingToCreate.getEmployeeId());
            stmt.setDate(3, bookingToCreate.getDate());
            stmt.setTime(4, bookingToCreate.getStart());
            stmt.setTime(5, bookingToCreate.getEnd());


            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }


    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        var sql = "select * from Bookings where customerId = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);

            try (var resultSet = stmt.executeQuery()) {
                while (resultSet.next()){
                    var id = resultSet.getInt("ID");
                    var customerID = resultSet.getInt("customerId");
                    var employeeID = resultSet.getInt("employeeId");
                    var date  = resultSet.getDate("date");
                    var start = resultSet.getTime("start");
                    var end = resultSet.getTime("end");
                    Booking booking = new Booking(id, customerID, employeeID, date, start, end);
                    bookingsForCustomer.add(booking);
                }
                return bookingsForCustomer;
            }
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        var sql = "select * from Bookings where employeeId = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);

            try (var resultSet = stmt.executeQuery()) {
                while (resultSet.next()){
                    var id = resultSet.getInt("ID");
                    var customerID = resultSet.getInt("customerId");
                    var employeeID = resultSet.getInt("employeeId");
                    var date  = resultSet.getDate("date");
                    var start = resultSet.getTime("start");
                    var end = resultSet.getTime("end");
                    Booking booking = new Booking(id, customerID, employeeID, date, start, end);
                    bookingsForEmployee.add(booking);
                }
                return bookingsForEmployee;
            }
        }
    }
    @Override
    public List<Booking> getBookings() throws SQLException {
        var sql = "select * from Bookings";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {

            try (var resultSet = stmt.executeQuery()) {
                while (resultSet.next()){
                    var id = resultSet.getInt("ID");
                    var customerID = resultSet.getInt("customerId");
                    var employeeID = resultSet.getInt("employeeId");
                    var date  = resultSet.getDate("date");
                    var start = resultSet.getTime("start");
                    var end = resultSet.getTime("end");
                    Booking booking = new Booking(id, customerID, employeeID, date, start, end);
                    bookingsForCustomer.add(booking);
                }
                return bookingsForCustomer;
            }
        }
    }

    @Override
    public void removeBookings() throws SQLException {
        var sql = "delete from Bookings";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.executeUpdate();
        }
    }
}
