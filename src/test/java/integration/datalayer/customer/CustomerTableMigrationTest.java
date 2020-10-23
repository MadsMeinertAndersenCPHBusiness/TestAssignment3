package integration.datalayer.customer;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.CustomerCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerTableMigrationTest {
    private CustomerStorage customerStorage;

    @BeforeAll
    public void Setup() throws SQLException {
        var url = "jdbc:mysql://localhost:3307/";
        var db = "DemoApplicationTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("5")
                .dataSource(url, "root", "testuser123"));

        flyway.migrate();

        customerStorage = new CustomerStorageImpl(url+db, "root", "testuser123");

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }
    }
    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber());
            customerStorage.createCustomer(c);
        }
    }

    @Test
    public void mustSaveCustomerInDatabaseWithPhoneNumberWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        customerStorage.createCustomer(new CustomerCreation("John","Carlssonn","513-123-312-312"));

        // Assert
        var customers = customerStorage.getCustomers();
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                                x.getLastname().equals("Carlssonn") &&
                                        x.getPhone().equals("513-123-312-312")));
    }
}
