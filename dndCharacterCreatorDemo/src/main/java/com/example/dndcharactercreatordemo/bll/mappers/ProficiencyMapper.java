package com.example.dndcharactercreatordemo.bll.mappers;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProficiencyMapper implements IMapper<ProficiencyDTO, Proficiency>{

    @Override
    public Proficiency fromDto(ProficiencyDTO proficiencyDTO) {
        if(proficiencyDTO==null)
            return null;
        Proficiency proficiency=new Proficiency();
        if (proficiencyDTO.id().isPresent())
            proficiency.setId(proficiencyDTO.id().get());
        proficiency.setIsDeleted(proficiencyDTO.isDeleted());
        proficiency.setName(proficiencyDTO.name());
        proficiency.setType(proficiencyDTO.type());
        return proficiency;
    }

    @Override
    public ProficiencyDTO toDto(Proficiency proficiency) {
        if(proficiency==null)
            return null;
        return new ProficiencyDTO(proficiency.getId().describeConstable(),
                proficiency.getIsDeleted(), proficiency.getName(),
                proficiency.getType());
    }

    @Override
    public List<ProficiencyDTO> toDTOs(List<Proficiency> proficiencies){
        return  proficiencies.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<Proficiency> fromDTOs(List<ProficiencyDTO> proficiencyDTOS){
        return  proficiencyDTOS.stream()
                .map(this::fromDto)
                .toList();
    }
}
