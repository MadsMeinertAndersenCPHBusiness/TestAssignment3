package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.CustomerCreation;
import dto.Employee;
import servicelayer.customer.CustomerServiceException;

import java.sql.SQLException;
import java.util.Date;

public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeStorage employeeStorage;

    public EmployeeServiceImpl(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }

    @Override
    public int createEmployee(Employee employee) throws EmployeeServiceException {
        return employee.getId();
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeServiceException, SQLException {
        return employeeStorage.getEmployeeWithId(employeeId);
    }
}
