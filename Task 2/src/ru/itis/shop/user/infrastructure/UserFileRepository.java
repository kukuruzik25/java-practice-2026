package ru.itis.shop.user.infrastructure;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line = reader.readLine();

            while ((line != null)) {
                User user = userMapper.fromLine(line);

                if (user.getEmail().equals(email)) {
                    System.out.println("Пользователь найден");
                    return Optional.of(user);
                }
                line = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String str = reader.readLine();

            while (str != null) {
                String[] info = str.split("\\|");

                if (info[0] != null) {
                    String currentId = info[0];

                    if (currentId.equals(id)) {
                        System.out.println("Пользователь найден");
                        User user = new User(currentId, info[1], info[2], info[3]);
                        return Optional.of(user);
                    }
                }
                str = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> updateProfileDescription(String email, String description) {

        User newUser = null;
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line = reader.readLine();

            while (line != null) {
                String[] parts = line.split("\\|");

                if (parts[1] != null) {
                    String currentEmail = parts[1];

                    if (currentEmail.equals(email)) {
                        newUser = new User(parts[0], parts[1], parts[2], description);
                        lines.add(userMapper.toLine(newUser));
                    } else {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String str : lines) {
                writer.write(str);
                writer.newLine();
            }

            return Optional.of(newUser);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
