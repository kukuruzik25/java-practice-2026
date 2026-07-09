package ru.itis.shop.app;

import java.sql.*;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:6435/postgres", "postgres", "723901")) {

            System.out.print("Введите email: ");
            String email = scanner.nextLine();
            System.out.print("Введите password: ");
            String password = scanner.nextLine();

            try (PreparedStatement prepareStatement = connection.prepareStatement("insert into account(email, password) values(?, ?)")) {

                prepareStatement.setString(1, email);
                prepareStatement.setString(2, password);

                int affectedRowsCount = prepareStatement.executeUpdate();

                if (affectedRowsCount != 1) {
                    throw new SQLException("Can`t insert");
                }

                System.out.println("Добавлено");
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
