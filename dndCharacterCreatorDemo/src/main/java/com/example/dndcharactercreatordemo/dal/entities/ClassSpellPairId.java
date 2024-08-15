package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ClassSpellPairId {
    @ManyToOne
    @JoinColumn(name="spell_id", nullable = false)
    private Spell spell;
    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    private DNDclass dndClass;
}
