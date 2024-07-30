package com.example.dndCharacterCreatorDemo.bll.dtos;

import com.example.dndCharacterCreatorDemo.dal.entities.Proficiency;
import com.example.dndCharacterCreatorDemo.dal.entities.Spell;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.Set;

public class ClassDTO extends BaseDTO{
    private String name;
    private String description;
    private int hitDice;
    private List<ProficiencyDTO> proficiencies;
    //private Set<Spell> spells;

    public ClassDTO() {
    }

    public ClassDTO(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHitDice() {
        return hitDice;
    }

    public void setHitDice(int hitDice) {
        this.hitDice = hitDice;
    }

    public List<ProficiencyDTO> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<ProficiencyDTO> proficiencies) {
        this.proficiencies = proficiencies;
    }
}
