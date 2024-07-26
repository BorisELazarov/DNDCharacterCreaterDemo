package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "class_spells")
public class ClassSpell {
    @Id
    @ManyToOne
    @JoinColumn(name="spell_id")
    private Spell spell;
    @Id
    @ManyToOne
    @JoinColumn(name="class_id")
    private DNDclass dndClass;
}
