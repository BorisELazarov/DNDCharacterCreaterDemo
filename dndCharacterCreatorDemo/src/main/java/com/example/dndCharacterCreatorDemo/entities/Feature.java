package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="features")
public class Feature extends BaseEntity{
    @Column
    private String name;
    @Column
    private String description;


}
