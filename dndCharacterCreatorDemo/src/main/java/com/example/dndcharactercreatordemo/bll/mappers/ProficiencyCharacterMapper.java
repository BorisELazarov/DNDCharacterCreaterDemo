package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.ProficiencyCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.entities.ProficiencyCharacter;
import com.example.dndcharactercreatordemo.dal.entities.ProficiencyCharacterPairId;

import java.util.List;

public class ProficiencyCharacterMapper implements IMapper<ProficiencyCharacterDTO, ProficiencyCharacter>{
    private final IMapper<ProficiencyDTO, Proficiency> mapper;

    public ProficiencyCharacterMapper(IMapper<ProficiencyDTO, Proficiency> mapper) {
        this.mapper = mapper;
    }

    @Override
    public ProficiencyCharacter fromDto(ProficiencyCharacterDTO dto) {
        ProficiencyCharacter proficiency=new ProficiencyCharacter();
        ProficiencyCharacterPairId id=new ProficiencyCharacterPairId();
        id.setProficiency(mapper.fromDto(dto.proficiency()));
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
    public List<ProficiencyCharacter> fromDTOs(List<ProficiencyCharacterDTO> dtos) {
        return dtos.stream().map(this::fromDto).toList();
    }

    @Override
    public List<ProficiencyCharacterDTO> toDTOs(List<ProficiencyCharacter> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
