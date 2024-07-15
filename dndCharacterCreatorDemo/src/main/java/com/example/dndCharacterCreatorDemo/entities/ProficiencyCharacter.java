package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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
    private boolean expertise;
}
