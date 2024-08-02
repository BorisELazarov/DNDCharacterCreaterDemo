package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.ProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

import java.util.List;

public class ProficiencyMapper implements IMapper<ProficiencyDTO, Proficiency>{

    @Override
    public Proficiency fromDto(ProficiencyDTO proficiencyDTO) {
        if(proficiencyDTO==null)
            return null;
        Proficiency proficiency=new Proficiency();
        if (proficiencyDTO.getId()!=null) {
            proficiency.setId(proficiencyDTO.getId());
            proficiency.setIsDeleted(proficiencyDTO.getIsDeleted());
        }
        proficiency.setName(proficiencyDTO.getName());
        proficiency.setType(proficiencyDTO.getType());
        return proficiency;
    }

    @Override
    public ProficiencyDTO toDto(Proficiency proficiency) {
        if(proficiency==null)
            return null;
        return new ProficiencyDTO(proficiency.getId(),
                proficiency.getIsDeleted(), proficiency.getName(),
                proficiency.getType());
    }

    @Override
    public List<ProficiencyDTO> toDtos(List<Proficiency> proficiencies){
        return  proficiencies.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<Proficiency> fromDtos(List<ProficiencyDTO> proficiencyDTOS){
        return  proficiencyDTOS.stream()
                .map(this::fromDto)
                .toList();
    }
}
