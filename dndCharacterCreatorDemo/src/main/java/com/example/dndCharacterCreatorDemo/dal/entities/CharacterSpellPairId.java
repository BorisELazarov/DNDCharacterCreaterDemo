package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CharacterSpellPairId {
    @ManyToOne
    @JoinColumn(name="spell_id")
    private Spell spell;

    @ManyToOne
    @JoinColumn(name="character_id")
    private Character character;
}
