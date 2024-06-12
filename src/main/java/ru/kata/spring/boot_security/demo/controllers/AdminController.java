package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @GetMapping
    public String adminHomePage(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/home";
    }

    @PatchMapping("/update")
    public String update(@RequestParam(name = "id") int id,
                         Model model,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        user.setId(id);
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUser(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/new";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(name = "id") int id) {
        userService.delete(userService.findById(id));
        return "redirect:/admin";
    }


//    @GetMapping("/index")
//    public String index(Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "admin/index";
//    }

//    @GetMapping("/profile")
//    public String profile(@RequestParam(name = "username") String email, Model model) {
//        model.addAttribute("user", userService.findByUsername(username));
//        return "admin/profile";
//    }

//    @PostMapping("/edit")
//    public String edit(@RequestParam(name = "username") String username, Model model) {
//        model.addAttribute("user", userService.findByUsername(username));
//        model.addAttribute("roles", roleService.getRoles());
//        return "admin/edit";
//    }






}