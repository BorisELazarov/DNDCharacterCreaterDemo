package com.example.dndcharactercreatordemo.bll.mappers.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.entities.Role;

import java.util.List;
import java.util.Optional;

public interface CharacterMapper {
    Character fromDto(CharacterDTO characterDTO, Optional<Role> role);
    CharacterDTO toDto(Character character);
    List<CharacterDTO> toDTOs(List<Character> characters);
}
