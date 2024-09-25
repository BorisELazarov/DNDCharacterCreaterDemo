package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Proficiency_Characters")
public class ProficiencyCharacter implements Serializable {
    @EmbeddedId
    private ProficiencyCharacterPairId id;
    @Column(name="expertise", nullable = false)
    private boolean expertise;

    public ProficiencyCharacterPairId getId() {
        return id;
    }

    public void setId(ProficiencyCharacterPairId id) {
        this.id = id;
    }

    public boolean getExpertise() {
        return expertise;
    }

    public void setExpertise(boolean expertise) {
        this.expertise = expertise;
    }
}
