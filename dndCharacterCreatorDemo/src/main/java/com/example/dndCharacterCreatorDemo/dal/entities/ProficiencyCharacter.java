package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name="Proficiency_Characters")
public class ProficiencyCharacter{
    @Id
    @ManyToOne
    @JoinColumn(name="proficiencyId")
    private Proficiency proficiency;
    @Id
    @ManyToOne
    @JoinColumn(name="characterId")
    private Character character;
    @Column(nullable = false)
    private boolean expertise;
}
