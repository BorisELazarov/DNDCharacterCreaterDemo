package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="proficiencies")
public class Proficiency extends BaseEntity {
    @Column(unique = true)
    private String name;
    @Column
    private String description;
//    @ManyToMany
//    @JoinTable(
//            name = "Proficiency_Characters",
//            joinColumns = { @JoinColumn(name = "proficiencyId") },
//            inverseJoinColumns = { @JoinColumn(name = "characterId") }
//    )
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
