package ru.javamentor.spring_boot_security.service;

import ru.javamentor.spring_boot_security.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getUserById(Long id);

    List<User> allUsers();

    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User findOneById(Long id);

    User findUserByFirstName(String name);

    User findByEmail(String email);

    Optional<User> findUserByEmail(String email);

}
