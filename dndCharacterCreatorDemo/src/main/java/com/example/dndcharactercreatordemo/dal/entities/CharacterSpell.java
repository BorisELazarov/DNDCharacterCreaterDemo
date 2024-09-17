package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "character_spells")
public class CharacterSpell{
    @EmbeddedId
    private CharacterSpellPairId id;

    public CharacterSpellPairId getId() {
        return id;
    }

    public void setId(CharacterSpellPairId id) {
        this.id = id;
    }
}
