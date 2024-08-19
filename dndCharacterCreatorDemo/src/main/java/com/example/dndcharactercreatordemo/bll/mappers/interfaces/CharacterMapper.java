package com.example.dndcharactercreatordemo.bll.mappers.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.dal.entities.Character;

import java.util.List;

public interface CharacterMapper {
    Character fromDto(CharacterDTO characterDTO);
    CharacterDTO toDto(Character character);
    List<Character> fromDTOs(List<CharacterDTO> characterDTOS);
    List<CharacterDTO> toDTOs(List<Character> characters);
}
