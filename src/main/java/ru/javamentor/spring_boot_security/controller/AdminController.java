package ru.javamentor.spring_boot_security.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.spring_boot_security.model.Role;
import ru.javamentor.spring_boot_security.model.User;
import ru.javamentor.spring_boot_security.service.RoleService;
import ru.javamentor.spring_boot_security.service.UserService;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("User roles: " + authentication.getAuthorities());
        }
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            model.addAttribute("user", new User());
            return "admin";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/user/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             @RequestParam(value = "roles", required = false) List<Long> roleId,
                             BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "redirect:/admin";
        }
        if (roleId != null) {
            List<Role> selectedRoles = roleService.getRoleById(roleId);
            user.setRoles(new HashSet<>(selectedRoles));
        } else {
            user.setRoles(new HashSet<>()); // Если роли не выбраны, очищаем список ролей
        }
        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long id,
                             @RequestParam(required = true, defaultValue = "") String action) {
        if (action.equals("delete")) {
            userService.deleteUser(id);
        }
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String viewUser(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user";
    }
}
