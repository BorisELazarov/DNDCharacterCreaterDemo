package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@MappedSuperclass
@Component
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    protected Long id;
    @Column(name="isDeleted")
    protected boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }
}
