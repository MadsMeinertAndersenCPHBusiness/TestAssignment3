package dto;

import java.sql.Date;
import java.sql.Time;

public class BookingCreation {
    private int customerId;
    private int employeeId;
    private java.sql.Date date;
    private Time start;
    private Time end;

    public BookingCreation(int customerId, int employeeId, Date date, Time start, Time end){
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public int getCustomerId(){ return customerId;}
    public int getEmployeeId(){return employeeId;}
    public java.sql.Date getDate(){return date;}
    public Time getStart(){return start;}
    public Time getEnd(){return end;}
}
