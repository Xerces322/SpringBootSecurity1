package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        for (Role role : user.getRoles()) {
            if (role.getRoleName().equals("ROLE_ADMIN")) {
                model.addAttribute("user", user);
                return "admin/profile";
            }
        }
        model.addAttribute("user", user);
        return "user/profile";
    }
}
