package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
public class ProficiencyCharacterPairId implements Serializable {
    @ManyToOne
    @JoinColumn(name="proficiency_id")
    @NotNull(message = "Proficiency must not be null")
    private Proficiency proficiency;

    @ManyToOne
    @JoinColumn(name="character_id")
    @NotNull(message = "Character must not be null")
    private Character character;

    public Proficiency getProficiency() {
        return proficiency;
    }

    public void setProficiency(Proficiency proficiency) {
        this.proficiency = proficiency;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
