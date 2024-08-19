package com.example.dndcharactercreatordemo.bll.mappers.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.characters.ProficiencyCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ProficiencyCharacterMapper;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.entities.ProficiencyCharacter;
import com.example.dndcharactercreatordemo.dal.entities.ProficiencyCharacterPairId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProficiencyCharacterMapperImpl implements ProficiencyCharacterMapper {
    private final ProficiencyMapper mapper;

    public ProficiencyCharacterMapperImpl(ProficiencyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ProficiencyCharacter fromDto(ProficiencyCharacterDTO dto, Character character) {
        ProficiencyCharacter proficiency=new ProficiencyCharacter();
        ProficiencyCharacterPairId id=new ProficiencyCharacterPairId();
        id.setProficiency(mapper.fromDto(dto.proficiency()));
        id.setCharacter(character);
        proficiency.setId(id);
        proficiency.setExpertise(dto.expertise());
        return proficiency;
    }

    @Override
    public ProficiencyCharacterDTO toDto(ProficiencyCharacter entity) {
        ProficiencyDTO proficiencyDTO=mapper.toDto(entity.getId().getProficiency());
        return new ProficiencyCharacterDTO(proficiencyDTO, entity.isExpertise());
    }

    @Override
    public List<ProficiencyCharacter> fromDTOs(List<ProficiencyCharacterDTO> dtos,
                                               Character character) {
        return dtos.stream().map(x->fromDto(x,character)).toList();
    }

    @Override
    public Set<ProficiencyCharacterDTO> toDTOs(List<ProficiencyCharacter> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
