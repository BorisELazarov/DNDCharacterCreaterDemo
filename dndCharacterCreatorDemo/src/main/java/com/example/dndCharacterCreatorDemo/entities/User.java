package com.example.dndCharacterCreatorDemo.entities;


import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User extends BaseEntity {
    @Column(name="username", unique = true)
    private String username;
    @Column(name="password")
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
