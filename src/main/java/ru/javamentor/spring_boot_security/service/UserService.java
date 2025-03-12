package ru.javamentor.spring_boot_security.service;

import ru.javamentor.spring_boot_security.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User findByUsername(String name);

}
