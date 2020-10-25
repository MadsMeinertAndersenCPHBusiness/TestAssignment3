package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateEmployeeTest {
    private EmployeeStorage employeeStorage;

    @AfterAll
    public void remove() throws SQLException {
        employeeStorage.removeEmployees();
    }

    @BeforeAll
    public void setup() throws SQLException {
        var url = "jdbc:mysql://localhost:3307/";
        var db = "DemoApplicationTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("2")
                .dataSource(url, "root", "testuser123"));

        flyway.migrate();
        //flyway.repair();

        employeeStorage = new EmployeeStorageImpl(url+db, "root", "testuser123");

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 100) {
            addFakeCustomers(100 - numEmployees);
        }
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName());
            employeeStorage.createEmployee(e);
        }
    }

    @Test
    public void mustIncreaseNumberOfRowWithOneWhenNewEmployeeIsCreated() throws SQLException {
        employeeStorage.createEmployee(new EmployeeCreation("John","Carlssonn"));
        var size = employeeStorage.getEmployees().size();
        assertEquals(size, 101);
    }
}
