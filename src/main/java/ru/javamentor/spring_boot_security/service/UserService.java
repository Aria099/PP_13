package ru.javamentor.spring_boot_security.service;

import ru.javamentor.spring_boot_security.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(Long id);

    List<User> allUsers();

    boolean saveUser(User user);

    boolean deleteUserById(Long id);

    void updateUser(User user);
}
