package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CharacterSpellPairId {
    @ManyToOne
    @JoinColumn(name="spell_id", nullable = false)
    private Spell spell;

    @ManyToOne
    @JoinColumn(name="character_id", nullable = false)
    private Character character;

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
