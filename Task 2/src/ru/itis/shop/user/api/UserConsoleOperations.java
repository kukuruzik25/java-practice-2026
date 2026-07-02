package ru.itis.shop.user.api;

import ru.itis.shop.application.UserService;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.infrastructure.UserFileRepository;
import ru.itis.shop.user.repository.UserRepository;

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
                findUserByEmail();
            }
            break;
            case "5": {
                updateUserProfileDescription();
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
        System.out.println("4. Найти пользователя по email");
        System.out.println("5. Обновить данные пользователя");
        System.out.println("0. Выход");
    }

    private void findUserById() {
        System.out.print("Введите id: ");
        String id = scanner.nextLine();
        userService.findUserById(id);
    }

    private void findUserByEmail() {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        userService.findUserByEmail(email);
    }

    private void updateUserProfileDescription() {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        if (!userService.findUserByEmail(email)) {
            return;
        }

        System.out.print("Введите новое описание профиля: ");
        String description = scanner.nextLine();

        userService.updateUserProfileDescription(email, description);
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите password: ");
        String password = scanner.nextLine();
        System.out.print("Введите описание профиля: ");
        String profileDescription = scanner.nextLine();

        userService.signUp(email, password, profileDescription);
    }

    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите password: ");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Неверный email или пароль");
        }
    }
}
