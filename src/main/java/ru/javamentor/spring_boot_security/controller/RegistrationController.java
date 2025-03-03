//package ru.javamentor.spring_boot_security.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import ru.javamentor.spring_boot_security.model.User;
//import ru.javamentor.spring_boot_security.service.UserService;
//
//@Controller
//public class RegistrationController {
//
//    private final UserService userService;
//
//    @Autowired
//    public RegistrationController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/registrationOld")
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//        return "registrationOld";
//    }
//
//    @PostMapping("/registrationOld")
//    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult br, Model model) {
//
//        if (br.hasErrors()) {
//            return "registrationOld";
//        }
//        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
//            model.addAttribute("passwordError", "Пароли не совпадают");
//            return "registrationOld";
//        }
//        if (!userService.saveUser(userForm)) {
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "registrationOld";
//        }
//        return "redirect:/";
//    }
//}
