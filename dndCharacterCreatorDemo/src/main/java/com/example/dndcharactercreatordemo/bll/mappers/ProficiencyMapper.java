package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ReadProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.SaveProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

import java.util.List;

public class ProficiencyMapper implements IMapper<SaveProficiencyDTO,ReadProficiencyDTO, Proficiency>{
    @Override
    public Proficiency fromDto(SaveProficiencyDTO proficiencyDTO) {
        if(proficiencyDTO==null)
            return null;
        Proficiency proficiency=new Proficiency();
        proficiency.setIsDeleted(proficiencyDTO.isDeleted());
        proficiency.setName(proficiencyDTO.name());
        proficiency.setType(proficiencyDTO.type());
        return proficiency;
    }

    @Override
    public Proficiency fromDto(SaveProficiencyDTO proficiencyDTO, Long id) {
        if(proficiencyDTO==null)
            return null;
        Proficiency proficiency=new Proficiency();
        proficiency.setId(id);
        proficiency.setIsDeleted(proficiencyDTO.isDeleted());
        proficiency.setName(proficiencyDTO.name());
        proficiency.setType(proficiencyDTO.type());
        return proficiency;
    }

    @Override
    public ReadProficiencyDTO toDto(Proficiency proficiency) {
        if(proficiency==null)
            return null;
        return new ReadProficiencyDTO(proficiency.getId(),
                proficiency.getIsDeleted(), proficiency.getName(),
                proficiency.getType());
    }

    @Override
    public List<ReadProficiencyDTO> toDtos(List<Proficiency> proficiencies){
        return  proficiencies.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<Proficiency> fromDtos(List<SaveProficiencyDTO> proficiencyDTOS){
        return  proficiencyDTOS.stream()
                .map(this::fromDto)
                .toList();
    }
}
