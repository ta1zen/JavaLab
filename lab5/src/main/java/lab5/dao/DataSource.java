package lab5.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataSource {

    private static HikariConfig config;
    private static HikariDataSource ds;

//    static {
//        try (InputStream input = MethodHandles.lookup().lookupClass().getClassLoader().getResourceAsStream("datasource.properties")) {
//            Properties prop = new Properties();
//            prop.load(input);
//            config = new HikariConfig(prop);
//            ds = new HikariDataSource(config);
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

    static {
        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl("jdbc:postgresql://localhost:5432/Hotel");
            config.setUsername("postgres");
            config.setPassword("1212");

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            ds = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to create connection pool!");
        }
    }



    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * Створює структуру таблиць у базі даних.
     */
    public static void createTablesStructure() throws SQLException {
        String createRoomTable = """
                CREATE TABLE IF NOT EXISTS rooms (
                    room_number_id INT PRIMARY KEY,
                    type VARCHAR(50) NOT NULL,
                    number_of_beds INT NOT NULL CHECK (number_of_beds >= 1),
                    amenities TEXT
                );
                """;

        String createCustomerTable = """
                CREATE TABLE IF NOT EXISTS customers (
                    document_id INT PRIMARY KEY,
                    last_name VARCHAR(50) NOT NULL,
                    first_name VARCHAR(50) NOT NULL,
                    birth_date DATE NOT NULL
                );
                """;

        String createBookingTable = """
                CREATE TABLE IF NOT EXISTS bookings (
                    id SERIAL PRIMARY KEY,
                    room_id INT NOT NULL,
                    customer_id INT NOT NULL,
                    start_date DATE NOT NULL,
                    end_date DATE NOT NULL,
                    is_paid BOOLEAN NOT NULL,
                    FOREIGN KEY (room_id) REFERENCES rooms (room_number_id),
                    FOREIGN KEY (customer_id) REFERENCES customers (document_id)
                );
                """;

        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            st.execute(createRoomTable);
            st.execute(createCustomerTable);
            st.execute(createBookingTable);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
