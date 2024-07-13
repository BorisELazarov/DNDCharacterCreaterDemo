package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table
public class Spell extends BaseEntity{
    @Column
    private String name;
    @Column
    private int level;
    @Column
    private String castingTime;
    @Column
    private int range;
    @Column
    private String target;
    @Column
    private String components;
    @Column
    private int duration;
    @Column
    private String description;
//    @ManyToMany
//    @JoinTable(
//            name = "Spell_Characters",
//            joinColumns = { @JoinColumn(name = "spellId") },
//            inverseJoinColumns = { @JoinColumn(name = "characterId") }
//    )
    @OneToMany(mappedBy = "spell")
    private Set<SpellCharacter> spellCharacters;
//    @ManyToMany
//    @JoinTable(
//            name = "Spell_Classes",
//            joinColumns = { @JoinColumn(name = "spellId") },
//            inverseJoinColumns = { @JoinColumn(name = "classId") }
//    )
    @OneToMany(mappedBy = "spell")
    private Set<SpellClass> spellClasses;
}
