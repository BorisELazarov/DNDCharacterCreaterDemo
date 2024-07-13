package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class SpellCharacter extends SoftDeletable {
    @Id
    @ManyToOne
    @JoinColumn(name="spellId")
    private Spell spell;
    @Id
    @ManyToOne
    @JoinColumn(name="characterId")
    private Character character;
}
