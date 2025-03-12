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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    throw new EntityNotFoundException("User not found with id: " + id);
                });
        userRepository.delete(userFromDb);
    }

    @Override
    public void updateUser(User user) {

        User userFromDb = userRepository.findById(user.getId())
                .orElseThrow(() -> {
                    logger.error("User not found");
                    throw new EntityNotFoundException("User not found with id: " + user.getId());
                });

        // Обновление полей
        userFromDb.setUsername(user.getUsername());
        userFromDb.setRoles(user.getRoles());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setLastname(user.getLastname());
        userFromDb.setAge(user.getAge());
        userFromDb.setPassword(user.getPassword());


        // Проверка изменения пароля
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // Если пароль изменен, кодируем его
            userFromDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            // Без изменений
            userFromDb.setPassword(userFromDb.getPassword());
        }
        userRepository.save(userFromDb);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}