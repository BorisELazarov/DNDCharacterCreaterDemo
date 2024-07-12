package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User extends BaseEntity {
    @Column(unique = true)
    private String username;
    @Column
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
