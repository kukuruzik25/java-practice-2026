package ru.itis.shop.api;

import ru.itis.shop.application.UserService;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.infrastructure.UserMapper;
import ru.itis.shop.user.infrastructure.UserRepositoryJdbcImpl;

public class Main {
    public static void main(String[] args) {
        UserRepositoryJdbcImpl userRepositoryJdbcImpl =  new UserRepositoryJdbcImpl();
        UserService userService = new UserService(userRepositoryJdbcImpl);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
