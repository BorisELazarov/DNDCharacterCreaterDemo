package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Classes")
public class DNDclass extends BaseEntity{
    @Column(name="name", unique = true)
    private String name;
    @Column(name="description", nullable = false)
    private String description;
    @Column(name="hit_dice", nullable = false)
    private int hitDice;
    @ManyToMany(mappedBy = "dndClasses")
    private Set<Proficiency> proficiencies;
    @OneToMany(mappedBy = "dndClass")
    private Set<ClassSpell> classSpells;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHitDice() {
        return hitDice;
    }

    public void setHitDice(int hitDice) {
        this.hitDice = hitDice;
    }
}
