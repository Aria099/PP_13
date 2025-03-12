package ru.javamentor.spring_boot_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.spring_boot_security.model.User;
import ru.javamentor.spring_boot_security.repository.UserRepository;
import ru.javamentor.spring_boot_security.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

//    @GetMapping("/api/user")
//    public ResponseEntity<User> userPage() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//
//        return ResponseEntity.ok(user);
//    }
//@GetMapping("/showAccount")
//public ResponseEntity<User> showUserAccount(Principal principal) {
//    System.out.println(principal.getName());
//    User user = userService.findUserByEmail(principal.getName()).get();
//    //System.out.println(principal.getName());
//    return new ResponseEntity<>(user, HttpStatus.OK);
//}
@GetMapping("/showAccount")
public ResponseEntity<User> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.findByUsername(username);
    return ResponseEntity.ok(user);
}
}
