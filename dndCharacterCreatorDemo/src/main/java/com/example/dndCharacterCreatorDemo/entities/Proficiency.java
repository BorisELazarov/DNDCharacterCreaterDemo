package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="proficiencies")
public class Proficiency extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @OneToMany(mappedBy = "proficiency")
    private Set<ProficiencyCharacter> proficiencyCharacters;
    @ManyToMany
    @JoinTable(
            name = "Proficiency_Classes",
            joinColumns = { @JoinColumn(name = "proficiencyId") },
            inverseJoinColumns = { @JoinColumn(name = "classId") }
    )
    private Set<DNDclass> dndClasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
