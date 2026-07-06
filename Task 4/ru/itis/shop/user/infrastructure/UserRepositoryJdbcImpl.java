package ru.itis.shop.user.infrastructure;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.*;

public class UserRepositoryJdbcImpl implements UserRepository {

    private static final String DB_URL = "jdbc:postgresql://localhost:6435/postgres";

    private static final String DB_USER = "postgres";

    private static final String DB_PASSWORD = "723901";

    @Override
    public void save(User user) {
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> updateProfileDescription(String email, String description) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){

            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from account")) {

                    while (resultSet.next()) {
                        String id = resultSet.getString("id");
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        String password = resultSet.getString("password");
                        String profileDescription = "";

                        try {
                            profileDescription = resultSet.getString("profile_description");
                        } catch (SQLException e) {
                            profileDescription = "";
                        }

                        User user = new User(id, name, email, password, profileDescription);
                        users.add(user);
                    }
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return users;
    }
}
