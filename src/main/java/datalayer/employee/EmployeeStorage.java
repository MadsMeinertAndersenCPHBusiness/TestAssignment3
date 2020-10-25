package datalayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface EmployeeStorage {
    int createEmployee(EmployeeCreation employee) throws SQLException;
    Employee getEmployeeWithId(int employeeId) throws SQLException;
    List<Employee> getEmployees() throws SQLException;
    void removeEmployees() throws SQLException;
}
