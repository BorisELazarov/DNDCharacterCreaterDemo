package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "class_spells")
public class ClassSpell {
    @EmbeddedId
    private ClassSpellPairId id;
}
