package lab5.dao;

import lab5.Customer;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class CustomerDao {
    private final Connection connection;
    static final String TABLE_NAME = "customers";

    public CustomerDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer add(Customer customer) throws SQLException {
        String insertCustomer = String.format("INSERT INTO %s (document_id, last_name, first_name, birth_date) VALUES (?, ?, ?, ?);", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(insertCustomer)) {
            ps.setInt(1, customer.getDocumentId());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getFirstName());
            ps.setDate(4, Date.valueOf(customer.getBirthDate())); // Перетворення LocalDate на SQL Date

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return customer; // Повертаємо об'єкт
            } else {
                throw new IllegalArgumentException("Error while creating customer " + customer);
            }
        }
    }

    public List<Customer> getAll() throws SQLException {
        String getAll = String.format("SELECT * FROM %s ORDER BY document_id;", TABLE_NAME);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(getAll);
        return getCustomersFromResultSet(rs);
    }

    private List<Customer> getCustomersFromResultSet(ResultSet rs) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer customer = fromResultSet(rs);
            customers.add(customer);
        }
        return customers;
    }

    private Customer fromResultSet(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("document_id"),
                rs.getDate("birth_date").toLocalDate() // Перетворення SQL Date на LocalDate
        );
    }

    public boolean delete(Customer customer) throws SQLException {
        String deleteCustomer = String.format("DELETE FROM %s WHERE document_id = ?;", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(deleteCustomer)) {
            ps.setInt(1, customer.getDocumentId());
            return ps.executeUpdate() > 0;
        }
    }

    public void clearCustomerTable() {
        String clearTableSQL = String.format("DELETE FROM %s;", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(clearTableSQL)) {
            int rowsAffected = ps.executeUpdate();
            System.out.println("Table cleared: " + rowsAffected + " rows");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error clearing table", e);
        }
    }

    public boolean update(Customer customer) throws SQLException {
        String updateCustomer = String.format(
                "UPDATE %s SET last_name = ?, first_name = ?, birth_date = ? WHERE document_id = ?;",
                TABLE_NAME
        );

        try (PreparedStatement ps = connection.prepareStatement(updateCustomer)) {
            ps.setString(1, customer.getLastName());
            ps.setString(2, customer.getFirstName());
            ps.setDate(3, Date.valueOf(customer.getBirthDate()));
            ps.setInt(4, customer.getDocumentId());

            return ps.executeUpdate() > 0;
        }
    }

    public Customer getById(int id) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE document_id = ?",
                TABLE_NAME
        );
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs); // Assuming CustomerDao has a similar fromResultSet method
            } else {
                throw new SQLException("Customer with ID " + id + " not found");
            }
        }
    }
}
