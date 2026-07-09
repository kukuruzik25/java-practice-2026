package ru.itis.shop.user.application;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String name, String email, String password, String profileDescription) {
        User user = new User(name, email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<UserDto> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {

            if (userRepository.checkPassword(email, password)) {
                return true;
            } else {
                return false;
            }

        } else {
            System.out.println("Пользователь не найден");
            return false;
        }
    }

    public Optional<UserDto> findUserById(Integer id) {
        Optional<UserDto> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserDto user = userOptional.get();
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    public void updateUserProfileDescription(String email, String newDescription) {
        userRepository.updateProfileDescription(email, newDescription);
    }

    public List<UserDto> findAll() {
        List<UserDto> users = userRepository.findAll();

        return users;
    }

    public List<UserDto> findAllByProfileDescription(String profileDescription) {
        List<UserDto> users = userRepository.findAllByProfileDescription(profileDescription);

        return users;
    }
}
