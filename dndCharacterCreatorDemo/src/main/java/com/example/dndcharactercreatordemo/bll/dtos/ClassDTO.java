package com.example.dndcharactercreatordemo.bll.dtos;

import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.ArrayList;
import java.util.List;

public class ClassDTO extends BaseDTO{

    private final String name;
    private final String description;
    private final HitDiceEnum hitDice;
    private final List<ProficiencyDTO> proficiencies;
    public ClassDTO(Long id, Boolean isDeleted, String name, String description,
                    HitDiceEnum hitDice, List<ProficiencyDTO> proficiencies) {
        super(id, isDeleted);
        this.name = name;
        this.description = description;
        this.hitDice = hitDice;
        this.proficiencies = proficiencies;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public HitDiceEnum getHitDice() {
        return hitDice;
    }

    public List<ProficiencyDTO> getProficiencies() {
        return new ArrayList<>(proficiencies);
    }
}
