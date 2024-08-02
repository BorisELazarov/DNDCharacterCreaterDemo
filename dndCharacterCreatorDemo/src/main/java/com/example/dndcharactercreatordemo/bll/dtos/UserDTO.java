package com.example.dndcharactercreatordemo.bll.dtos;

public class UserDTO extends BaseDTO{

    private final String username;
    private final String password;
    private final String role;

    public UserDTO(Long id, String username, String password,
                   String role, Boolean isDeleted) {
        super(id, isDeleted);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
