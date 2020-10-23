package datalayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;
import java.util.Collection;

public interface EmployeeStorage {
    int createEmployee(EmployeeCreation employee) throws SQLException;
    Collection<Employee> getEmployeeWithId(int employeeId);
}
