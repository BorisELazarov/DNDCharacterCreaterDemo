package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="users")
public class User extends BaseEntity {
    @Column(name="username", nullable = false)
    private String username;
    @Column(name="password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Character> characters;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="role_id", referencedColumnName = "id")
    private Role role;

    public User() {
    }

    public User(String username, String password) {
        this.username=username;
        this.password=password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
