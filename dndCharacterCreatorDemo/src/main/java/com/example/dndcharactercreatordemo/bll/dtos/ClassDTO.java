package com.example.dndcharactercreatordemo.bll.dtos;

import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.List;

public class ClassDTO extends BaseDTO{
    private String name;
    private String description;
    private HitDiceEnum hitDice;
    private List<ProficiencyDTO> proficiencies;

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

    public HitDiceEnum getHitDice() {
        return hitDice;
    }

    public void setHitDice(HitDiceEnum hitDice) {
        this.hitDice = hitDice;
    }

    public List<ProficiencyDTO> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<ProficiencyDTO> proficiencies) {
        this.proficiencies = proficiencies;
    }
}
