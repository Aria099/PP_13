package ru.javamentor.spring_boot_security.service;

import ru.javamentor.spring_boot_security.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> allUsers();

    boolean saveUser(User user);

    boolean deleteUser(Long id);

    boolean updateUser(Long id, User updatedUser);
}
