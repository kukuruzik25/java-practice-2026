package ru.itis.shop.user.api;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findUserById();
            }
            break;
            case "4": {
                updateUserProfileDescription();
            }
            break;
            case "5": {
                findAll();
            }
            break;
            case "6": {
                findUserAllByProfileDescription();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить описание пользователя по почте");
        System.out.println("5. Получить информацию обо всех пользователях");
        System.out.println("6. Показать информацию о пользователях с заданным описанием профиля");
        System.out.println("0. Выход");
    }

    private void findUserById() {
        System.out.print("Введите id: ");
        Integer id = Integer.valueOf(scanner.nextLine());
        userService.findUserById(id);
    }

    private void updateUserProfileDescription() {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите новое описание профиля: ");
        String description = scanner.nextLine();

        userService.updateUserProfileDescription(email, description);
    }

    private void findAll() {
        List<UserDto> users = userService.findAll();

        System.out.println("~~ Список всех пользователей ~~");
        for (UserDto user : users) {
            System.out.println("ID: " + user.getId() +
                            ", Name: " + user.getName() +
                            ", Email: " + user.getEmail());
        }
        System.out.println();
    }

    private void findUserAllByProfileDescription() {
        System.out.print("Введите искомое описание профиля: ");
        String description = scanner.nextLine();

        List<UserDto> users = userService.findAllByProfileDescription(description);

        System.out.println("~~ Список пользователей ~~");
        for (UserDto user : users) {
            System.out.println("ID: " + user.getId() +
                    ", Name: " + user.getName() +
                    ", Email: " + user.getEmail());
        }
        System.out.println();
    }


    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя...");
        System.out.print("Введите name: ");
        String name = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите password: ");
        String password = scanner.nextLine();
        System.out.print("Введите описание профиля: ");
        String profileDescription = scanner.nextLine();

        userService.signUp(name, email, password, profileDescription);

        System.out.println("Пользователь зарегистрирован");
    }

    private void signIn() {
        System.out.println("Вы можете войти в приложение...");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите password: ");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение!");
        } else {
            System.out.println("Неверный email или пароль!");
        }
    }
}
