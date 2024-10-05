package lab1;

import java.util.Objects;
import java.time.LocalDate;
/**
 * Represents a customer with their personal details such as last name, first name,
 * documentId number, and birthdate.
 */
public class Customer {
    private final String lastName;
    private final String firstName;
    private final int documentId;
    private final LocalDate birthDate;

    public Customer(String lastName, String firstName, int documentId, LocalDate birthDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.documentId = documentId;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", documentId='" + documentId + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return documentId == customer.documentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId);
    }
}

