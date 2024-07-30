package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "character_spells")
public class CharacterSpell {
    @EmbeddedId
    private CharacterSpellPairId id;
}
