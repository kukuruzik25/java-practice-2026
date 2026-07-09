package ru.itis.shop.user.repository;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findById(Integer id);

    void updateProfileDescription(String email, String description);

    List<UserDto> findAll();

    List<UserDto> findAllByProfileDescription(String profileDescription);

    boolean checkPassword(String email, String password);
}
