package com.example.dndCharacterCreatorDemo.bll.dtos;

import com.example.dndCharacterCreatorDemo.dal.entities.DNDclass;
import com.example.dndCharacterCreatorDemo.dal.entities.ProficiencyCharacter;
import jakarta.persistence.*;

import java.util.Set;

public class ProficiencyDTO extends BaseDTO{
    private String name;
    private String type;
    //private Set<ProficiencyCharacter> proficiencyCharacters;
    //private Set<DNDclass> dndClasses;


    public ProficiencyDTO() {
    }

    public ProficiencyDTO(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
