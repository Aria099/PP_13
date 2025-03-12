package ru.javamentor.spring_boot_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javamentor.spring_boot_security.model.Role;
import ru.javamentor.spring_boot_security.model.User;
import ru.javamentor.spring_boot_security.repository.RoleRepository;
import ru.javamentor.spring_boot_security.repository.UserRepository;

import java.util.Set;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findUserByEmail("admin@mail.ru") == null) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
            }

            // Создаём пользователя-администратора
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@mail.ru");
            admin.setLastname("admin");
            admin.setAge(12);
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(adminRole, userRole));

            userRepository.save(admin);

            System.out.println("Администратор успешно создан!");
        } else {
            System.out.println("Администратор уже существует.");
        }
        // Создаём пользователя
        User one = new User();
        one.setUsername("fff");
        one.setEmail("fff@mail.ru");
        one.setLastname("fff");
        one.setAge(12);
        one.setPassword(passwordEncoder.encode("fff"));
        one.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));

        userRepository.save(one);
        System.out.println("User added");
    }
}