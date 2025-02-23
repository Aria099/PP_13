package ru.javamentor.spring_boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.spring_boot_security.model.User;
import ru.javamentor.spring_boot_security.service.UserServiceImpl;

import java.util.Optional;

@Controller
public class AdminController {

    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long id,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUserById(id);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

    @GetMapping("admin/edit")
    public String updateUserById(@RequestParam("id") Long id, Model model) {
        Optional<User> userId = userService.getUserById(id);
        if (userId.isPresent()) {
            model.addAttribute("user", userId.get());
            return "edit";
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/user")
    public String viewUser(@RequestParam("id") Long id, Model model) {
        Optional<User> userId = userService.getUserById(id);
        if (userId.isPresent()) {
            model.addAttribute("user", userId.get());
            return "user";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("user") User user,
                           BindingResult br) {
        if (br.hasErrors()) {
            return "edit";
        } else {
            userService.updateUser(user);
            return "redirect:/admin";
        }
    }
}
