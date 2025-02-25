package ru.javamentor.spring_boot_security.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.spring_boot_security.model.Role;
import ru.javamentor.spring_boot_security.model.User;
import ru.javamentor.spring_boot_security.service.RoleService;
import ru.javamentor.spring_boot_security.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;

@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @GetMapping("admin/edit")
    public String updateUser(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> allRoles = roleService.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           @RequestParam(value = "roles", required = false) List<Long> roleId,
                           BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "edit";
        }
        if (roleId != null) {
            List<Role> selectedRoles = roleService.getRoleById(roleId);
            user.setRoles(new HashSet<>(selectedRoles));
        }
        if (user.getId() == null) {
            userService.saveUser(user);
        } else {
            userService.updateUser(user.getId(), user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long id,
                             @RequestParam(required = true, defaultValue = "") String action) {
        if (action.equals("delete")) {
            userService.deleteUser(id);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/user")
    public String viewUser(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user";
    }
}
