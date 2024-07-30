package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Roles")
public class Role extends BaseEntity{
    private String title;
    @ManyToMany
    @JoinTable(
            name = "Role_privileges",
            joinColumns = { @JoinColumn(name = "roleId") },
            inverseJoinColumns = { @JoinColumn(name = "privilegeId") }
    )
    private List<Privilege> privileges;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
