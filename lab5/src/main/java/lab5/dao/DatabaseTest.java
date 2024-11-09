package lab5.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Hotel";
        String username = "postgres";
        String password = "1212";

        // Підключення до бази даних
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Успішне підключення до бази!");

            // Створення таблиці
            String createTableSQL = "CREATE TABLE IF NOT EXISTS test_table (" +
                    "id SERIAL PRIMARY KEY" +
                    ");";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSQL);
                System.out.println("Таблиця успішно створена або вже існує.");
            } catch (Exception e) {
                System.out.println("Помилка при створенні таблиці:");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Помилка при підключенні до бази:");
            e.printStackTrace();
        }
    }
}
