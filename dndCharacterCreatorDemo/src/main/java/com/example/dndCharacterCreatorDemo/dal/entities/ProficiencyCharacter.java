package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name="Proficiency_Characters")
public class ProficiencyCharacter{
    @Id
    @ManyToOne
    @JoinColumn(name="proficiency_id")
    private Proficiency proficiency;
    @Id
    @ManyToOne
    @JoinColumn(name="character_id")
    private Character character;
    @Column(name="expertise", nullable = false)
    private boolean expertise;
}
