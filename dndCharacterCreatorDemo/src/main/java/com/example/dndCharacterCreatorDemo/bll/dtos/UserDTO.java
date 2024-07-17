package com.example.dndCharacterCreatorDemo.bll.dtos;

import com.example.dndCharacterCreatorDemo.dal.entities.User;

public class UserDTO extends BaseDTO{
    private String username;
    private String password;

    public UserDTO() {
        super();
    }

    public UserDTO(Long id) {
        super(id);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
