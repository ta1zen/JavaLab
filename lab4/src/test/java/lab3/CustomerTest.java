package lab3;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.time.LocalDate;

public class CustomerTest {

    @DataProvider(name = "customerDataToString")
    public Object[][] customerDataToString() {
        return new Object[][] {
                {
                        new Customer("Doe", "John", 12345, LocalDate.of(1990, 1, 1)),
                        "Customer{lastName='Doe', firstName='John', documentId='12345', birthDate='1990-01-01'}"
                }
        };
    }

    @Test(dataProvider = "customerDataToString")
    public void testToString(Customer customer, String expected) {
        assertEquals(customer.toString(), expected);
    }

    @DataProvider(name = "customerDataEquals")
    public Object[][] customerDataEquals() {
        return new Object[][]{
                {
                        new Customer("Doe", "John", 12345, LocalDate.of(1990, 1, 1)),
                        new Customer("Doe", "John", 12345, LocalDate.of(1990, 1, 1)),
                        true
                },
                {
                        new Customer("Doe", "Jane", 12346, LocalDate.of(1990, 1, 1)),
                        new Customer("Doe", "John", 12345, LocalDate.of(1990, 1, 1)),
                        false
                }
        };
    }

    @Test(dataProvider = "customerDataEquals")
    public void testEquals(Customer customer1, Customer customer2, boolean expected) {
        assertEquals(customer1.equals(customer2), expected);
    }

    @Test(dataProvider = "customerDataEquals")
    public void testHashCode(Customer customer1, Customer customer2, boolean expected) {
        if (expected) {
            assertEquals(customer1.hashCode(), customer2.hashCode());
        } else {
            assertNotEquals(customer1.hashCode(), customer2.hashCode());
        }
    }
}
