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
    public String adminHomePage() {
        return "admin/home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/index";
    }

    @GetMapping("/profile")
    public String profile(@RequestParam(name = "username") String username, Model model) {
        model.addAttribute("user", userService.findByUsername(username));
        return "admin/profile";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam(name = "username") String username, Model model) {
        model.addAttribute("user", userService.findByUsername(username));
        model.addAttribute("roles", roleService.getRoles());
        return "admin/edit";
    }

    @PatchMapping("/update")
    public String update(@RequestParam(name = "selectedRole") String roleName, @RequestParam(name = "id") int id,
                         Model model,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userValidator.setEdit(true);
        user.setId(id);
        userValidator.validate(user, bindingResult);
        userValidator.setEdit(false);
        Role role = roleService.getRole(roleName);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getRoles());
            return "admin/edit";
        }
        userService.update(user, role);
        return "redirect:/admin/profile?username=" + user.getUsername();
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/new";
    }

    @PostMapping("/create")
    public String create(@RequestParam(name = "selectedRole") String roleName,
                         Model model,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        Role role = roleService.getRole(roleName);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getRoles());
            return "admin/new";
        }
        userService.save(user, role);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(name = "username") String username) {
        userService.delete(userService.findByUsername(username));
        return "redirect:/admin/index";
    }
}
