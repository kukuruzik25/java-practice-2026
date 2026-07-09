package shop.user.infrastructure.persistence.jdbc;

import shop.infrastructure.persistence.jdbc.RowMapper;
import shop.user.api.dto.UserDto;
import shop.user.domain.User;
import shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    private final RowMapper<User> userRowMapper = row -> new User(
            row.getInt("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("password"),
            row.getString("profiledescription")
    );

    private final RowMapper<UserDto> userDtoRowMapper = row -> new UserDto(
            row.getInt("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("profiledescription")

    );

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user) {
        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:6435/postgres", "postgres", "723901")) {

            try (PreparedStatement prepareStatement = connection.prepareStatement("insert into account(name, email, password, profiledescription) values(?, ?, ?, ?)")) {

                prepareStatement.setString(1, user.getName());
                prepareStatement.setString(2, user.getEmail());
                prepareStatement.setString(3, user.getPassword());
                prepareStatement.setString(4, user.getProfileDescription());

                int affectedRowsCount = prepareStatement.executeUpdate();

                if (affectedRowsCount != 1) {
                    throw new SQLException("Can`t insert");
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public  Optional<UserDto> findByEmail(String email) {
        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:6435/postgres", "postgres", "723901")) {

            try (PreparedStatement prepareStatement = connection.prepareStatement("select * from account where email = ?")) {

                prepareStatement.setString(1, email);

                try (ResultSet resultSet = prepareStatement.executeQuery()) {
                    if (resultSet.next()) {

                        return Optional.of(userDtoRowMapper.mapRow(resultSet));

                    } else {
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<UserDto> findById(Integer id) {
        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:6435/postgres", "postgres", "723901")) {

            try (PreparedStatement prepareStatement = connection.prepareStatement("select * from account where id = ?")) {

                prepareStatement.setInt(1, id);

                try (ResultSet resultSet = prepareStatement.executeQuery()) {

                    if (resultSet.next()) {

                        return Optional.of(userDtoRowMapper.mapRow(resultSet));

                    } else {
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void updateProfileDescription(String email, String description) {
        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:6435/postgres", "postgres", "723901")) {

            try (PreparedStatement prepareStatement = connection.prepareStatement("update account set profiledescription = ? where email = ?")) {

                prepareStatement.setString(1, description);
                prepareStatement.setString(2, email);

                int affectedRowsCount = prepareStatement.executeUpdate();

                if (affectedRowsCount != 1) {
                    throw new SQLException("Can`t update");
                }

            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<UserDto> findAll() {

        List<UserDto> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()){
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from account")) {

                    while (resultSet.next()) {
                        users.add(userDtoRowMapper.mapRow(resultSet));
                    }
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return users;
    }

    @Override
    public List<UserDto> findAllByProfileDescription(String profileDescription) {

        List<UserDto> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from account where profileDescription = ?")) {

                preparedStatement.setString(1, profileDescription);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        users.add(userDtoRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return users;
    }

    @Override
    public boolean checkPassword(String email, String password) {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select password from account where email = ?")) {

                preparedStatement.setString(1, email);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    if (resultSet.next()) {
                        String realPessword = resultSet.getString("password");

                        return realPessword.equals(password);
                    }
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        System.out.println("Пользователь не найден");
        return false;
    }

}
