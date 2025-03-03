package ru.javamentor.spring_boot_security.service;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.javamentor.spring_boot_security.model.User;
import ru.javamentor.spring_boot_security.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUserById(Long id) {

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    throw new EntityNotFoundException("User not found with id: " + id);
                });

        return userFromDb;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findUserByEmail(user.getEmail());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long id) {

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    throw new EntityNotFoundException("User not found with id: " + id);
                });

        userRepository.delete(userFromDb);
        return true;
    }

    @Override
    public boolean updateUser(Long id, User updatedUser) {

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    throw new EntityNotFoundException("User not found with id: " + id);
                });
        // Проверяем, изменился ли username
        if (!userFromDb.getEmail().equals(updatedUser.getEmail())) {
            // Если username изменился, проверяем его уникальность
            User existingUser = userRepository.findUserByEmail(updatedUser.getEmail());
            if (existingUser != null) {
                return false;
            }
        }
        // Обновление полей
        userFromDb.setUsername(updatedUser.getUsername());
        userFromDb.setRoles(updatedUser.getRoles());
        userFromDb.setEmail(updatedUser.getEmail());
        userFromDb.setLastname(updatedUser.getLastname());
        userFromDb.setAge(updatedUser.getAge());


        // Проверка изменения пароля
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            // Если пароль изменен, кодируем его
            userFromDb.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        } else {
            // Без изменений
            userFromDb.setPassword(userFromDb.getPassword());
        }
        userRepository.save(userFromDb);
        return true;
    }
}
