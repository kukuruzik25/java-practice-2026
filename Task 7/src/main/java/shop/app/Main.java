package shop.app;

import shop.infrastructure.persistence.jdbc.DriverManagerDataSource;
import shop.user.api.UserConsoleOperations;
import shop.user.application.UserService;
import shop.user.infrastructure.persistence.jdbc.UserRepositoryJdbcImpl;
import shop.user.repository.UserRepository;
import shop.util.PropertiesReader;

import javax.sql.DataSource;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        PropertiesReader propertiesReader = new PropertiesReader("application.properties");

        Properties properties = propertiesReader.loadProperties();

        String dbUrl = properties.getProperty("db.url");
        String dbUser = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");

        DataSource dataSource  = new DriverManagerDataSource(dbUrl, dbUser, dbPassword);

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        UserService userService = new UserService(userRepository);
        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
