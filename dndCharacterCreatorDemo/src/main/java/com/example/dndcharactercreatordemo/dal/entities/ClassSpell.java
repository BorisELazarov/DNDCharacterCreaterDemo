package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "class_spells")
public class ClassSpell implements Serializable {
    @EmbeddedId
    private ClassSpellPairId id;
}
