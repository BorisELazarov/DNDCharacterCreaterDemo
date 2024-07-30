package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "privileges")
public class Privilege extends BaseEntity{
    private String title;
}
