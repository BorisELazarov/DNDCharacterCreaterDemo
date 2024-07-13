package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@MappedSuperclass
public class BaseEntity extends SoftDeletable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Long id;

    public Long getId() {
        return id;
    }

}
