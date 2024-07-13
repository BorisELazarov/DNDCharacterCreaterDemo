package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
    @OneToMany(mappedBy = "dndClass")
    private Set<ProficiencyClass> proficiencyClasses;
    @OneToMany(mappedBy = "dndClass")
    private Set<SpellClass> spellClasses;
}
