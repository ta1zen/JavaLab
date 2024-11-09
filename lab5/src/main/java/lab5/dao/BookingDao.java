package lab5.dao;

import lab5.Booking;
import lab5.Customer;
import lab5.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {
    private final Connection connection;
    static final String TABLE_NAME = "bookings";

    public BookingDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Booking add(Booking booking) throws SQLException {
        String insertBooking = String.format(
                "INSERT INTO %s (room_id, customer_id, start_date, end_date, is_paid) VALUES (?, ?, ?, ?, ?);",
                TABLE_NAME
        );
        try (PreparedStatement ps = connection.prepareStatement(insertBooking, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getRoom().getRoomNumber());
            ps.setInt(2, booking.getCustomer().getDocumentId());
            ps.setDate(3, Date.valueOf(booking.getStartDate()));
            ps.setDate(4, Date.valueOf(booking.getEndDate()));
            ps.setBoolean(5, booking.isPaid());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return booking; // Повертаємо об'єкт
            } else {
                throw new IllegalArgumentException("Error while creating room " + booking);
            }
        }
    }

    public List<Booking> getAll() throws SQLException {
        String getAll = String.format("SELECT * FROM %s ORDER BY id;", TABLE_NAME);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(getAll);
        return getBookingsFromResultSet(rs);
    }

    private List<Booking> getBookingsFromResultSet(ResultSet rs) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        while (rs.next()) {
            Booking booking = fromResultSet(rs);
            bookings.add(booking);
        }
        return bookings;
    }

    private Booking fromResultSet(ResultSet rs) throws SQLException {
        int roomId = rs.getInt("room_id");
        int customerId = rs.getInt("customer_id");

        RoomDao roomDao = new RoomDao();
        CustomerDao customerDao = new CustomerDao();

        Room room = roomDao.getById(roomId);
        Customer customer = customerDao.getById(customerId);

        return new Booking(
                room,
                customer,
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getBoolean("is_paid")
        );
    }

    public boolean delete(Booking booking) throws SQLException {
        String deleteBooking = String.format("DELETE FROM %s WHERE room_id = ? and customer_id = ?;", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(deleteBooking)) {
            ps.setInt(1, booking.getRoom().getRoomNumber());
            ps.setInt(2, booking.getCustomer().getDocumentId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Booking booking) throws SQLException {
        String updateBooking = String.format(
                "UPDATE %s SET room_id = ?, customer_id = ?, start_date = ?, end_date = ?, is_paid = ? WHERE room_id = ? and customer_id = ?;",
                TABLE_NAME
        );
        try (PreparedStatement ps = connection.prepareStatement(updateBooking)) {
            ps.setInt(1, booking.getRoom().getRoomNumber());
            ps.setInt(2, booking.getCustomer().getDocumentId());
            ps.setDate(3, Date.valueOf(booking.getStartDate()));
            ps.setDate(4, Date.valueOf(booking.getEndDate()));
            ps.setBoolean(5, booking.isPaid());
            ps.setInt(6, booking.getRoom().getRoomNumber());
            ps.setInt(7, booking.getCustomer().getDocumentId());



            return ps.executeUpdate() > 0;
        }
    }

    public void clearRoomTable() {
        String clearTableSQL = String.format("DELETE FROM %s;", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(clearTableSQL)) {
            int rowsAffected = ps.executeUpdate();
            System.out.println("Table cleared: " + rowsAffected + " rows");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error clearing table", e);
        }
    }
}
