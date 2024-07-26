package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

import java.util.Set;

@Entity
@Table(name = "spells")
public class Spell extends BaseEntity{
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="level", nullable = false)
    private int level;
    @Column(name="casting_time", nullable = false)
    private String castingTime;
    @Column(name="casting_range")
    private int castingRange;
    @Column(name="target")
    private String target;
    @Column(name="components", nullable = false)
    private String components;
    @Column(name="duration", nullable = false)
    private int duration;
    @Column(name="description", nullable = false)
    private String description;
    @OneToMany(mappedBy = "spell")
    private Set<CharacterSpell> characterSpells;

    @OneToMany(mappedBy = "spell")
    private Set<ClassSpell> classSpells;
}
