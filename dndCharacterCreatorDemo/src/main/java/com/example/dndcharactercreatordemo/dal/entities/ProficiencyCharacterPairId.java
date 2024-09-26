package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;

@Embeddable
public class ProficiencyCharacterPairId{
    @ManyToOne
    @JoinColumn(name="proficiency_id",nullable = false)
    private Proficiency proficiency;

    @ManyToOne
    @JoinColumn(name="character_id",nullable = false)
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
