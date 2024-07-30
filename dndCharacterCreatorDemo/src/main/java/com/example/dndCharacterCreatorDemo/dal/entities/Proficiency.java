package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="proficiencies")
public class Proficiency extends BaseEntity {
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="type", nullable = false)
    private String type;
//    @OneToMany(mappedBy = "proficiency")
//    private List<ProficiencyCharacter> proficiencyCharacters;
//    @ManyToMany(mappedBy = "proficiencies")
//    private List<DNDclass> dndClasses;

    public Proficiency() {
    }

    public Proficiency(String name, String type) {

        this.name = name;
        this.type = type;
    }

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
