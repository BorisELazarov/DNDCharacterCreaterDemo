package com.example.dndcharactercreatordemo.bll.mappers.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.CharacterSpellMapper;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.SpellMapper;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.entities.CharacterSpell;
import com.example.dndcharactercreatordemo.dal.entities.CharacterSpellPairId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CharacterSpellImpl implements CharacterSpellMapper {
    private final SpellMapper spellMapper;

    public CharacterSpellImpl(SpellMapper spellMapper) {
        this.spellMapper = spellMapper;
    }

    @Override
    public CharacterSpell fromDto(SpellDTO spellDTO, Character character) {
        CharacterSpell characterSpell=new CharacterSpell();
        CharacterSpellPairId id=new CharacterSpellPairId();
        id.setSpell(spellMapper.fromDto(spellDTO));
        id.setCharacter(character);
        characterSpell.setId(id);
        return characterSpell;
    }

    @Override
    public List<CharacterSpell> fromDTOs(Set<SpellDTO> spellDTOs, Character character) {
        return spellDTOs.stream().map(x->fromDto(x,character)).toList();
    }

    @Override
    public SpellDTO fromDto(CharacterSpell characterSpell) {
        return spellMapper.toDto(characterSpell.getId().getSpell());
    }

    @Override
    public Set<SpellDTO> toDTOs(List<CharacterSpell> characterSpells) {
        return characterSpells.stream().map(this::fromDto).collect(Collectors.toSet());
    }
}
