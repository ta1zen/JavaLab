package lab5;

import lab5.dao.DataSource;
import lab5.dao.RoomDao;
import lab5.dao.CustomerDao;
import lab5.dao.BookingDao;

import lab5.Room;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        DataSource.createTablesStructure();


        RoomDao roomDao = new RoomDao();
        BookingDao bookingDao = new BookingDao();
        CustomerDao customerDao = new CustomerDao();

        bookingDao.clearRoomTable();
        customerDao.clearCustomerTable();
        //roomDao.clearRoomTable();



        Room room1 = new Room("Deluxe", 2, 101, "Wi-Fi, TV");
        Room room2 = new Room("Standard", 1, 102, "Wi-Fi");

        roomDao.add(room1);
        roomDao.add(room2);

        System.out.println("All rooms:");
        List<Room> rooms = roomDao.getAll();
        rooms.forEach(room -> System.out.println(room));

        room1.setType("Standart");
        roomDao.update(room1);

        System.out.println("All rooms after update:");
        rooms = roomDao.getAll();
        rooms.forEach(room -> System.out.println(room));

        roomDao.delete(room1);

        System.out.println("All rooms after delete:");
        rooms = roomDao.getAll();
        rooms.forEach(room -> System.out.println(room));


        //CustomerDao

        Customer customer1 = new Customer( "Doe", "John", 1, LocalDate.of(1990, 5, 15));
        Customer customer2 = new Customer( "Smith", "Anna", 2, LocalDate.of(1985, 8, 20));

        customerDao.add(customer1);
        customerDao.add(customer2);

        System.out.println("All customers:");
        List<Customer> customers = customerDao.getAll();
        customers.forEach(customer -> System.out.println(customer));

        customer1.setLastName("Johnson");
        customerDao.update(customer1);

        System.out.println("All customers after update:");
        customers = customerDao.getAll();
        customers.forEach(customer -> System.out.println(customer));

        customerDao.delete(customer1);

        System.out.println("All customers after delete:");
        customers = customerDao.getAll();
        customers.forEach(customer -> System.out.println(customer));


        //BookingDao

        Booking booking1 = new Booking(room2, customer2, LocalDate.of(2024, 11, 10), LocalDate.of(2024, 11, 15), true);
        bookingDao.add(booking1);

        System.out.println("All bookings:");
        List<Booking> bookings = bookingDao.getAll();
        bookings.forEach(booking -> System.out.println(booking));

        booking1.setPaid(false);
        bookingDao.update(booking1);

        System.out.println("All bookings after update:");
        bookings = bookingDao.getAll();
        bookings.forEach(booking -> System.out.println(booking));

        bookingDao.delete(booking1);

        System.out.println("All bookings after delete:");
        bookings = bookingDao.getAll();
        bookings.forEach(booking -> System.out.println(booking));


    }
}

