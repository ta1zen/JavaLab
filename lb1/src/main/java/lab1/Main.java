package lab1;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Room room1 = new Room.RoomBuilder()
                .setType("Suite")
                .setNumberOfBeds(2)
                .setRoomNumber(101)
                .setAmenities("Wi-Fi, TV, Mini-bar")
                .build();

        Customer customer1 = new Customer("Smith", "John", 123456, LocalDate.of(1990, 5, 15));
        Customer customer2 = new Customer("Smith", "John", 1234056, LocalDate.of(1990, 5, 15));

        Booking booking1 = new Booking(room1, customer1, LocalDate.of(2024,9,27), LocalDate.of(2024,9,30), true);

        System.out.println(booking1);
        System.out.println(customer1.equals(customer2));
    }
}
