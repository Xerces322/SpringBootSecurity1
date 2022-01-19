package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;


@Controller
public class MainController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @DeleteMapping("admin/delete")
    public String delete(@RequestParam(name = "username") String username) {
        userService.delete(userService.findByUsername(username));
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/user")
    public String userForAdmin(@RequestParam(name = "username") String username, Model model) {
        model.addAttribute("user", userService.findByUsername(username));
        return "userForAdmin";
    }

    @GetMapping("/admin/edit")
    public String edit(@RequestParam(name = "username") String username, Model model) {
        model.addAttribute("user", userService.findByUsername(username));
        return "editForAdmin";
    }

    @PatchMapping("/update")
    public String update(@RequestParam(name = "id") int id, @ModelAttribute("user") User user) {
        user.setId(id);
        userService.update(user);
        return "redirect:/admin/user?username=" + user.getUsername();
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/index")
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/newUser")
    public String newUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/index";
    }


}
