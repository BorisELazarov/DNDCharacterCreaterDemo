package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table
public class DNDclass extends BaseEntity{
    @Column(unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private int hitDice;
    @ManyToMany(mappedBy = "dndClasses")
    private Set<Proficiency> proficiencies;
    @ManyToMany(mappedBy = "dndClasses")
    private Set<Spell> spells;
}
