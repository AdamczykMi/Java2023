package org.chatbot.database;

import java.sql.*;

public class DatabaseConnection implements IDatabaseConnection {
    private Connection connection;

    // TODO: Implementacja połączenia z bazą danych
    public DatabaseConnection(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void addReservation(String customerName, String reservationTime, int numberOfGuests) throws SQLException {
        // TODO: Implementacja metody dodającej rezerwację do bazy danych
        //  Użyj try with resource lub zamknij statement
        String sql = "INSERT INTO reservations (customer_name, reservation_time, number_of_guests) VALUES (customer_name, reservation_time, number_of_guests)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customerName);
            statement.setString(2, reservationTime);
            statement.setInt(3, numberOfGuests);

            statement.executeUpdate();
        }
    }

    @Override
    public void deleteReservation(int reservationId) throws SQLException {
        // TODO: Implementacja metody usuwającej rezerwację z bazy danych
        //  Użyj try with resource lub zamknij statement
        String sql = "DELETE FROM reservations WHERE reservation_id = ?";
        // ...
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reservationId);

            statement.executeUpdate();
        }
    }

    @Override
    public ResultSet listReservations() throws SQLException {
        // TODO: Implementacja metody zwracającej listę rezerwacji z bazy danych
        //  Nie zamykaj w tym miejscu ResultSet.
        String sql = "SELECT * FROM reservations";

        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    // TODO: Metoda do zamknięcia połączenia z bazą danych
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
