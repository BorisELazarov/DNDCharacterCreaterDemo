package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ProficiencyCharacterPairId implements Serializable {
    @ManyToOne
    @JoinColumn(name="proficiency_id")
    private Proficiency proficiency;

    @ManyToOne
    @JoinColumn(name="character_id")
    private Character character;
}
