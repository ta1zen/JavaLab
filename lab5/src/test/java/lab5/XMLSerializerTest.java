package lab5;

import lab5.Serializers.XMLSerializer;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class XMLSerializerTest {
    private XMLSerializer<Customer> xmlSerializer;
    private Customer customer;

    @BeforeTest
    void setUp() {
        xmlSerializer = new XMLSerializer<>(Customer.class);
        customer = new Customer("Doe", "John", 12345, LocalDate.of(1990, 1, 1));
    }

    @Test
    void serializeSingleObject() throws IOException {
        String xmlString = xmlSerializer.serialize(customer);

        assertNotNull(xmlString, "XML string is null");
        String[] expectedValues = {
                "<lastName>Doe</lastName>",
                "<firstName>John</firstName>",
                "<documentId>12345</documentId>",
                "<birthDate>1990-01-01</birthDate>"
        };
        for (String expected : expectedValues) {
            assertTrue(xmlString.contains(expected), "Expected XML to contain: " + expected);
        }
    }

    @Test
    void serializeList() throws IOException {
        List<Customer> customers = List.of(
                new Customer("Smith", "Jane", 67890, LocalDate.of(1985, 5, 20)),
                customer
        );

        String xmlString = xmlSerializer.serialize(customers);

        assertNotNull(xmlString, "XML string is null");
        String[] expectedValues = {
                "<lastName>Doe</lastName>",
                "<lastName>Smith</lastName>",
                "<documentId>12345</documentId>",
                "<birthDate>1985-05-20</birthDate>"
        };
        for (String expected : expectedValues) {
            assertTrue(xmlString.contains(expected), "Expected XML to contain: " + expected);
        }
    }

    @Test
    void deserializeSingleObject() throws IOException {
        String xmlString = """
                <Customer>
                    <lastName>Doe</lastName>
                    <firstName>John</firstName>
                    <documentId>12345</documentId>
                    <birthDate>1990-01-01</birthDate>
                </Customer>""";
        Customer deserializedCustomer = xmlSerializer.deserialize(xmlString);

        assertNotNull(deserializedCustomer, "Deserialized customer is null");
        assertEquals(deserializedCustomer.getLastName(), "Doe");
        assertEquals(deserializedCustomer.getFirstName(), "John");
        assertEquals(deserializedCustomer.getDocumentId(), 12345);
        assertEquals(deserializedCustomer.getBirthDate(), LocalDate.of(1990, 1, 1));
    }

    @Test
    void deserializeList() throws IOException {
        String xmlString = """
                <Customers>
                    <Customer>
                        <lastName>Doe</lastName>
                        <firstName>John</firstName>
                        <documentId>12345</documentId>
                        <birthDate>1990-01-01</birthDate>
                    </Customer>
                    <Customer>
                        <lastName>Smith</lastName>
                        <firstName>Jane</firstName>
                        <documentId>67890</documentId>
                        <birthDate>1985-05-20</birthDate>
                    </Customer>
                </Customers>""";
        List<Customer> deserializedCustomers = xmlSerializer.deserializeToList(xmlString);

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
        String fileName = "src/test/resources/Serialization-write-customer.xml";
        xmlSerializer.writeToFile(customer, fileName);

        File file = new File(fileName);
        assertTrue(file.exists(), "File not created");

        String fileContent = Files.readString(Paths.get(fileName));
        assertNotNull(fileContent, "File content is null");

        assertTrue(fileContent.contains("<lastName>Doe</lastName>"), "Expected 'lastName' not found");
        assertTrue(fileContent.contains("<firstName>John</firstName>"), "Expected 'firstName' not found");
        assertTrue(fileContent.contains("<documentId>12345</documentId>"), "Expected 'documentId' not found");
        assertTrue(fileContent.contains("<birthDate>1990-01-01</birthDate>"), "Expected 'birthDate' not found");
    }

    @Test
    void writeListToFile() throws IOException {
        List<Customer> customers = List.of(
                new Customer("Smith", "Jane", 67890, LocalDate.of(1985, 5, 20)),
                customer
        );
        String fileName = "src/test/resources/Serialization-write-customer-list.xml";
        xmlSerializer.writeToFile(customers, fileName);

        File file = new File(fileName);
        assertTrue(file.exists(), "File doesn't exist");

        String fileContent = Files.readString(Paths.get(fileName));
        assertNotNull(fileContent, "File content is null");

        assertTrue(fileContent.contains("<lastName>Smith</lastName>"), "Expected 'lastName' for Smith not found");
        assertTrue(fileContent.contains("<firstName>Jane</firstName>"), "Expected 'firstName' for Smith not found");
        assertTrue(fileContent.contains("<documentId>67890</documentId>"), "Expected 'documentId' for Smith not found");
        assertTrue(fileContent.contains("<birthDate>1985-05-20</birthDate>"), "Expected 'birthDate' for Smith not found");

        assertTrue(fileContent.contains("<lastName>Doe</lastName>"), "Expected 'lastName' for Doe not found");
        assertTrue(fileContent.contains("<firstName>John</firstName>"), "Expected 'firstName' for Doe not found");
        assertTrue(fileContent.contains("<documentId>12345</documentId>"), "Expected 'documentId' for Doe not found");
        assertTrue(fileContent.contains("<birthDate>1990-01-01</birthDate>"), "Expected 'birthDate' for Doe not found");
    }

    @Test
    void readFromFileMultipleCustomers() throws IOException {
        String fileName = "src/test/resources/Serialization-read-customer-list.xml";

        List<Customer> deserializedCustomers = xmlSerializer.readFromFile(fileName);

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
