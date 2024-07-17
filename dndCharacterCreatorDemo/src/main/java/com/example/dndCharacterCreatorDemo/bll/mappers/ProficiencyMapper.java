package com.example.dndCharacterCreatorDemo.bll.mappers;

import com.example.dndCharacterCreatorDemo.bll.dtos.ProficiencyDTO;
import com.example.dndCharacterCreatorDemo.dal.entities.Proficiency;

public class ProficiencyMapper implements IMapper<ProficiencyDTO, Proficiency>{

    @Override
    public Proficiency fromDto(ProficiencyDTO proficiencyDTO) {
        Proficiency proficiency=new Proficiency();
        proficiency.setIsDeleted(proficiencyDTO.getIsDeleted());
        proficiency.setType(proficiencyDTO.getType());
        proficiency.setName(proficiencyDTO.getName());
        return proficiency;
    }

    @Override
    public ProficiencyDTO toDto(Proficiency proficiency) {
        ProficiencyDTO proficiencyDTO=new ProficiencyDTO(proficiency.getId());
        proficiencyDTO.setName(proficiency.getName());
        proficiencyDTO.setType(proficiency.getType());
        proficiencyDTO.setIsDeleted(proficiencyDTO.getIsDeleted());
        return proficiencyDTO;
    }
}
