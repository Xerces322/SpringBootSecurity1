package ru.kata.spring.boot_security.demo.util;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Component
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    @Setter
    private boolean edit = false;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (!edit) {
            if (userRepository.existsByUsername(user.getUsername()) && !edit) {
                errors.rejectValue("username", "", "Username is already in use");
            } else if (userRepository.existsByEmail(user.getEmail())) {
                errors.rejectValue("email", "", "Email is already in use");
            }
        } else {
            if (userRepository.existsByUsernameAndIdIsNot(user.getUsername(), user.getId())) {
                errors.rejectValue("username", "", "Username is already in use");
            } else if (userRepository.existsByEmailAndIdIsNot(user.getEmail(), user.getId())) {
                errors.rejectValue("email", "", "Email is already in use");
            }
        }
    }
}
