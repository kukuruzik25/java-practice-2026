package ru.itis.shop.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return (user.getPassword().equals(password)|user.getEmail().equals(email));
        } else {
            return false;
        }
    }

    public boolean findUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getId().equals(id);
        } else {
            return false;
        }
    }

    public boolean findUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getEmail().equals(email);
        } else {
            return false;
        }
    }

    public boolean updateUserProfileDescription(String email, String newDescription) {
        Optional<User> userOptional = userRepository.updateProfileDescription(email, newDescription);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Данные обновлены");
            return true;
        } else {
            System.out.println("Не удалось обновить данные");
            return false;
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
