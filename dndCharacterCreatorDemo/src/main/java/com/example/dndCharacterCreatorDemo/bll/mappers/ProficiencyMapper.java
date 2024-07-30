package com.example.dndCharacterCreatorDemo.bll.mappers;

import com.example.dndCharacterCreatorDemo.bll.dtos.ProficiencyDTO;
import com.example.dndCharacterCreatorDemo.dal.entities.Proficiency;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ProficiencyDTO> toDtos(List<Proficiency> proficiencies){
        return  proficiencies.stream()
                .map(x-> toDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<Proficiency> fromDtos(List<ProficiencyDTO> proficiencyDTOS){
        return  proficiencyDTOS.stream()
                .map(x->fromDto(x))
                .collect(Collectors.toList());
    }
}
