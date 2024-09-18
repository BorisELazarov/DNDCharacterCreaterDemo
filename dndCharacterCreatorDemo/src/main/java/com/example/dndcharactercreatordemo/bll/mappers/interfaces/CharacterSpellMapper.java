package com.example.dndcharactercreatordemo.bll.mappers.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.entities.CharacterSpell;

import java.util.List;
import java.util.Set;

public interface CharacterSpellMapper {
    CharacterSpell fromDto(SpellDTO spellDTO, Character character);
    List<CharacterSpell> fromDTOs(Set<SpellDTO> spellDTO, Character character);
    SpellDTO toDto(CharacterSpell characterSpell);

    Set<SpellDTO> toDTOs(List<CharacterSpell> characterSpells);
}
