package ru.kata.spring.boot_security.demo.dto;

public class RoleDTO {
    private int id;
    private String roleName;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }
}
