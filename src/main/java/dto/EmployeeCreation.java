package dto;

import java.sql.Date;

public class EmployeeCreation {
    private String firstname;
    private String lastname;
    private java.sql.Date birthdate;

    public EmployeeCreation(String firstName, String lastName, Date birthdate){
        this.firstname = firstName;
        this.lastname = lastName;
        this.birthdate = birthdate;
    }

    public EmployeeCreation(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public java.sql.Date getBirthday(){return birthdate;}
}
