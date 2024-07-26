package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Roles")
public class Role extends BaseEntity{
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
