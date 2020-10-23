package dto;

public class CustomerCreation {
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() { return phone; }

    public String firstname, lastname, phone;

    public CustomerCreation(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public CustomerCreation(String firstname, String lastname, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }
}
