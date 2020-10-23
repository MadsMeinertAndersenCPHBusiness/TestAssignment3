package dto;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.sql.Time;
import java.util.Date;

public class Booking {
    private int id;
    private int customerId;
    private int employeeId;
    private Date date;
    private Time start;
    private Time end;

    public Booking(int id, int customerId, int employeeId, Date date, Time start, Time end){
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public int getId(){ return id; }
    public int getCustomerId(){ return customerId;}
    public int getEmployeeId(){return employeeId;}
    public Date getDate(){return date;}
    public Time getStart(){return start;}
    public Time getEnd(){return end;}
}
