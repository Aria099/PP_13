package ru.javamentor.spring_boot_security.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.spring_boot_security.model.Role;
import ru.javamentor.spring_boot_security.model.User;
import ru.javamentor.spring_boot_security.repository.UserRepository;
import ru.javamentor.spring_boot_security.service.RoleService;
import ru.javamentor.spring_boot_security.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserRepository userRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() { //Authentication authentication
//        if (authentication != null && authentication.isAuthenticated()) {
//            System.out.println("User roles: " + authentication.getAuthorities());
//        }
//        List<User> users = userService.allUsers();
//        if (users.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }
//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return userService.allUsers();
//    }


    @GetMapping("/showAccount")
    public ResponseEntity<User> getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }


//    @GetMapping("/showAccount")
//    public ResponseEntity<User> showInfoUser(Principal principal) {
//        System.out.println(principal.getName());
//        return new ResponseEntity<>(userService.findByEmail(principal.getName()), HttpStatus.OK);
//    }


    @PostMapping("/users")
    public ResponseEntity<?> addNewUser(@RequestBody @Valid User user) {

        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user) {

        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        userService.deleteUser(id);
        return new ResponseEntity<>("User was deleted.", HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findOneById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() { //ResponseEntity<Collection<Role>>
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
    @GetMapping("/roles/{id}")
    public ResponseEntity<Collection<Role>> getRole(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findOneById(id).getRoles(), HttpStatus.OK);
    }
}