package dto;

import java.sql.Date;

public class Employee {
    private int id;
    private String firstname;
    private String lastname;
    private java.sql.Date birthdate;

    public Employee(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Employee(int id, String firstName, String lastName, Date birthdate){
        this.id = id;
        this.firstname = firstName;
        this.lastname = lastName;
        this.birthdate = birthdate;
    }
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public java.sql.Date getBirthday(){return birthdate;}
}
