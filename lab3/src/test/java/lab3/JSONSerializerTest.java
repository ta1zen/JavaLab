package lab3;

import lab3.Serializers.JSONSerializer;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class JSONSerializerTest {
    private JSONSerializer<Customer> jsonSerializer;
    private Customer customer;

    @BeforeTest
    void setUp() {
        jsonSerializer = new JSONSerializer<>(Customer.class);
        customer = new Customer("Doe", "John", 12345, LocalDate.of(1990, 1, 1));
    }

    @Test
    void serializeSingleObject() throws IOException {
        String jsonString = jsonSerializer.serialize(customer);

        assertNotNull(jsonString, "JSON string is null");
        String[] expectedValues = {
                "\"lastName\":\"Doe\"",
                "\"firstName\":\"John\"",
                "\"documentId\":12345",
                "\"birthDate\":\"1990-01-01\""
        };
        for (String expected : expectedValues) {
            assertTrue(jsonString.contains(expected), "Expected JSON to contain: " + expected);
        }
    }

    @Test
    void serializeList() throws IOException {
        List<Customer> customers = List.of(
                new Customer("Smith", "Jane", 67890, LocalDate.of(1985, 5, 20)),
                customer
        );

        String jsonString = jsonSerializer.serialize(customers);

        assertNotNull(jsonString, "JSON string is null");
        String[] expectedValues = {
                "\"lastName\":\"Doe\"",
                "\"lastName\":\"Smith\"",
                "\"documentId\":12345",
                "\"birthDate\":\"1985-05-20\""
        };
        for (String expected : expectedValues) {
            assertTrue(jsonString.contains(expected), "Expected JSON to contain: " + expected);
        }
    }

    @Test
    void deserializeSingleObject() throws IOException {
        String jsonString = """
                {
                    "lastName": "Doe",
                    "firstName": "John",
                    "documentId": 12345,
                    "birthDate": "1990-01-01"
                }""";
        Customer deserializedCustomer = jsonSerializer.deserialize(jsonString);

        assertNotNull(deserializedCustomer, "Deserialized customer is null");
        assertEquals(deserializedCustomer.getLastName(), "Doe");
        assertEquals(deserializedCustomer.getFirstName(), "John");
        assertEquals(deserializedCustomer.getDocumentId(), 12345);
        assertEquals(deserializedCustomer.getBirthDate(), LocalDate.of(1990, 1, 1));
    }

    @Test
    void deserializeList() throws IOException {
        String jsonString = """
                [
                    {
                        "lastName": "Doe",
                        "firstName": "John",
                        "documentId": 12345,
                        "birthDate": "1990-01-01"
                    },
                    {
                        "lastName": "Smith",
                        "firstName": "Jane",
                        "documentId": 67890,
                        "birthDate": "1985-05-20"
                    }
                ]""";
        List<Customer> deserializedCustomers = jsonSerializer.deserializeToList(jsonString);

        assertNotNull(deserializedCustomers, "Deserialized customers list is null");
        assertEquals(deserializedCustomers.size(), 2);
        assertTrue(deserializedCustomers.stream().anyMatch(
                customer -> "Doe".equals(customer.getLastName()) &&
                        "John".equals(customer.getFirstName()) &&
                        customer.getDocumentId() == 12345 &&
                        LocalDate.of(1990, 1, 1).equals(customer.getBirthDate())
        ), "Expected customer 'John Doe' not found in the list");
    }

    @Test
    void writeToFile() throws IOException {
        String fileName = "src/test/resources/Serialization-write-customer.json";
        jsonSerializer.writeToFile(customer, fileName);

        File file = new File(fileName);
        assertTrue(file.exists(), "File not created");

        String fileContent = Files.readString(Paths.get(fileName));
        assertNotNull(fileContent, "File content is null");

        assertTrue(fileContent.contains("\"lastName\":\"Doe\""), "Expected 'lastName' not found");
        assertTrue(fileContent.contains("\"firstName\":\"John\""), "Expected 'firstName' not found");
        assertTrue(fileContent.contains("\"documentId\":12345"), "Expected 'documentId' not found");
        assertTrue(fileContent.contains("\"birthDate\":\"1990-01-01\""), "Expected 'birthDate' not found");
    }

    @Test
    void writeListToFile() throws IOException {
        List<Customer> customers = List.of(
                new Customer("Smith", "Jane", 67890, LocalDate.of(1985, 5, 20)),
                customer
        );
        String fileName = "src/test/resources/Serialization-write-customer-list.json";
        jsonSerializer.writeToFile(customers, fileName);

        File file = new File(fileName);
        assertTrue(file.exists(), "File doesn't exist");

        String fileContent = Files.readString(Paths.get(fileName));
        assertNotNull(fileContent, "File content is null");

        assertTrue(fileContent.contains("""
                "lastName" : "Smith" """));
        assertTrue(fileContent.contains("""
                "firstName" : "Jane" """));
        assertTrue(fileContent.contains("""
                "documentId" : 67890 """));
        assertTrue(fileContent.contains("""
                "birthDate" : "1985-05-20" """));

        assertTrue(fileContent.contains("""
                "lastName" : "Doe" """));
        assertTrue(fileContent.contains("""
                "firstName" : "John" """));
        assertTrue(fileContent.contains("""
                "documentId" : 12345 """));
        assertTrue(fileContent.contains("""
                "birthDate" : "1990-01-01" """));

    }


    @Test
    void readFromFileMultipleCustomers() throws IOException {
        String fileName = "src/test/resources/Serialization-read-customer-list.json";

        List<Customer> deserializedCustomers = jsonSerializer.readFromFile(fileName);

        assertNotNull(deserializedCustomers, "Deserialized customers list is null");
        assertEquals(deserializedCustomers.size(), 2);
        assertTrue(deserializedCustomers.stream().anyMatch(customer ->
                "Doe".equals(customer.getLastName()) &&
                        "John".equals(customer.getFirstName()) &&
                        customer.getDocumentId() == 12345 &&
                        LocalDate.of(1990, 1, 1).equals(customer.getBirthDate())
        ), "Expected customer 'John Doe' not found in the list");
    }
}
