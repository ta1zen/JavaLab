package lab3;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.util.Objects;
import java.time.LocalDate;

/**
 * Represents a customer with their personal details such as last name, first name,
 * documentId number, and birthdate.
 */
@Getter
//@Builder
public class Customer {
    private String lastName;
    private String firstName;
    private int documentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public Customer() {
    }


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

