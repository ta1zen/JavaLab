package lab5.dao;

import lab5.Room;

import java.sql.*;
import java.util.*;

public class RoomDao {
    private final Connection connection;
    static final String TABLE_NAME = "rooms";

    public RoomDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Room add(Room room) throws SQLException {
        String insertRoom = String.format("INSERT INTO %s (room_number_id, type, number_of_beds, amenities) VALUES (?, ?, ?, ?);", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(insertRoom)) {
            ps.setInt(1, room.getRoomNumber());
            ps.setString(2, room.getType());
            ps.setInt(3, room.getNumberOfBeds());
            ps.setString(4, room.getAmenities());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return room; // Повертаємо об'єкт
            } else {
                throw new IllegalArgumentException("Error while creating room " + room);
            }
        }
    }

    public List<Room> getAll() throws SQLException {
        String getAll = String.format("SELECT * FROM %s ORDER BY room_number_id;", TABLE_NAME);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(getAll);
        return getRoomsFromResultSet(rs);
    }

    private List<Room> getRoomsFromResultSet(ResultSet rs) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (rs.next()) {
            Room room = fromResultSet(rs);
            rooms.add(room);
        }
        return rooms;
    }

    private Room fromResultSet(ResultSet rs) throws SQLException {
        return new Room(
                rs.getString("type"),
                rs.getInt("number_of_beds"),
                rs.getInt("room_number_id"),
                rs.getString("amenities")
        );
    }

    public boolean delete(Room room) throws SQLException {
        String deleteRoom = String.format("DELETE FROM %s WHERE room_number_id = ?;", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(deleteRoom)) {
            ps.setInt(1, room.getRoomNumber());
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

    public boolean update(Room room) throws SQLException {
        String updateRoom = String.format(
                "UPDATE %s SET type = ?, number_of_beds = ?, amenities = ? WHERE room_number_id = ?;",
                TABLE_NAME
        );

        try (PreparedStatement ps = connection.prepareStatement(updateRoom)) {
            ps.setString(1, room.getType());
            ps.setInt(2, room.getNumberOfBeds());
            ps.setString(3, room.getAmenities());
            ps.setInt(4, room.getRoomNumber());

            return ps.executeUpdate() > 0;
        }
    }

    public Room getById(int id) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE room_number_id = ?",
                TABLE_NAME
        );
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            } else {
                throw new SQLException("Room with ID " + id + " not found");
            }
        }
    }
}
