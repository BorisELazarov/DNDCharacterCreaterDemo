package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "character_spells")
public class CharacterSpell {
    @Id
    @ManyToOne
    @JoinColumn(name="spell_id")
    private Spell spell;
    @Id
    @ManyToOne
    @JoinColumn(name="character_id")
    private Character character;
}
