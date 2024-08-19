package com.example.dndcharactercreatordemo.bll.mappers.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.characters.ProficiencyCharacterDTO;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.entities.ProficiencyCharacter;

import java.util.List;
import java.util.Set;

public interface ProficiencyCharacterMapper{
    ProficiencyCharacter fromDto(ProficiencyCharacterDTO dto, Character character);
    ProficiencyCharacterDTO toDto(ProficiencyCharacter entity);
    List<ProficiencyCharacter> fromDTOs(List<ProficiencyCharacterDTO> dtos, Character character);
    Set<ProficiencyCharacterDTO> toDTOs(List<ProficiencyCharacter> entities);
}
