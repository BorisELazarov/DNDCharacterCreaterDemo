package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ClassSpellPairId {
    @ManyToOne
    @JoinColumn(name="spell_id")
    private Spell spell;
    @ManyToOne
    @JoinColumn(name="class_id")
    private DNDclass dndClass;
}
