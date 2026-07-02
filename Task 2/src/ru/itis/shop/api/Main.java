package ru.itis.shop.api;

import ru.itis.shop.application.UserService;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.infrastructure.UserFileRepository;
import ru.itis.shop.user.infrastructure.UserMapper;

public class Main {
    public static void main(String[] args) {
        UserFileRepository userFileRepository =  new UserFileRepository("users.txt", new UserMapper());
        UserService userService = new UserService(userFileRepository);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
