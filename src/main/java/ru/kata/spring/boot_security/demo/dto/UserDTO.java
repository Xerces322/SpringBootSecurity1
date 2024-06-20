package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.entities.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserDTO {
    private int id;

    @NotEmpty(message = "first name should not be empty")
    private String firstName;

    @NotEmpty(message = "last name should not be empty")
    private String lastName;

    @Min(value = 1, message = "age should be more than 0")
    private int age;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @NotEmpty(message = "password should not be empty")
    private String password;

    private List<RoleDTO> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDTO> getRoles() {
        return this.roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
