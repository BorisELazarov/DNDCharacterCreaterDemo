package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

import java.util.Set;

@Entity
@Table(name = "spells")
public class Spell extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int level;
    @Column(nullable = false)
    private String castingTime;
    @Column(name="casting_range")
    private int castingRange;
    @Column
    private String target;
    @Column(nullable = false)
    private String components;
    @Column(nullable = false)
    private int duration;
    @Column(nullable = false)
    private String description;
    @ManyToMany
    @JoinTable(
            name = "Character_Spells",
            joinColumns = { @JoinColumn(name = "spellId") },
            inverseJoinColumns = { @JoinColumn(name = "characterId") }
    )
    private Set<Character> characters;
    @ManyToMany
    @JoinTable(
            name = "Class_Spells",
            joinColumns = { @JoinColumn(name = "spellId") },
            inverseJoinColumns = { @JoinColumn(name = "classId") }
    )
    private Set<DNDclass> dndClasses;
}
