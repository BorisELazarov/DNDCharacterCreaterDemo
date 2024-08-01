package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.ProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

import java.util.List;
import java.util.stream.Collectors;

public class ProficiencyMapper implements IMapper<ProficiencyDTO, Proficiency>{

    @Override
    public Proficiency fromDto(ProficiencyDTO proficiencyDTO) {
        if(proficiencyDTO==null)
            return null;
        Proficiency proficiency=new Proficiency(proficiencyDTO.getId());
        proficiency.setIsDeleted(proficiencyDTO.getIsDeleted());
        proficiency.setType(proficiencyDTO.getType());
        proficiency.setName(proficiencyDTO.getName());
        return proficiency;
    }

    @Override
    public ProficiencyDTO toDto(Proficiency proficiency) {
        if(proficiency==null)
            return null;
        ProficiencyDTO proficiencyDTO=new ProficiencyDTO();
        proficiencyDTO.setId(proficiency.getId());
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
