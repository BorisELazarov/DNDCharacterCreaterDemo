package com.example.dndcharactercreatordemo.bll.mappers.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.characters.ProficiencyCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.*;
import com.example.dndcharactercreatordemo.dal.entities.*;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapperImpl implements CharacterMapper {
    private final UserMapper userMapper;
    private final ClassMapper classMapper;
    private final ProficiencyCharacterMapper charProfMapper;
    private final CharacterSpellMapper characterSpellMapper;

    public CharacterMapperImpl(@NotNull UserMapper userMapper, @NotNull  ClassMapper classMapper,
                               @NotNull ProficiencyCharacterMapper charProfMapper,
                               @NotNull CharacterSpellMapper characterSpellMapper) {
        this.userMapper = userMapper;
        this.classMapper = classMapper;
        this.charProfMapper = charProfMapper;
        this.characterSpellMapper = characterSpellMapper;
    }



    @Override
    public Character fromDto(CharacterDTO dto, Optional<Role> role) {
        if (dto.name().isEmpty())
            throw new IllegalArgumentException("Character's name must not be null");
        Character character = new Character();
        dto.id().ifPresent(character::setId);
        character.setIsDeleted(dto.isDeleted());
        character.setName(dto.name());
        character.setLevel(dto.level());
        character.setBaseStr(dto.baseStr());
        character.setBaseDex(dto.baseDex());
        character.setBaseCon(dto.baseCon());
        character.setBaseInt(dto.baseInt());
        character.setBaseWis(dto.baseWis());
        character.setBaseCha(dto.baseCha());
        character.setUser(userMapper.fromDto(dto.user(), role));
        character.setDNDclass(classMapper.fromDto(dto.dndClass()));
        character.setProficiencyCharacters(getProficiencyCharacters(dto.proficiencies(), character));
        character.setCharacterSpells(getCharacterSpells(dto.spells(),character));
        return character;
    }

    private List<ProficiencyCharacter> getProficiencyCharacters(Set<ProficiencyCharacterDTO> dtos,
                                                                Character character) {
        return charProfMapper.fromDTOs(dtos.stream().toList(),character);
    }

    private List<CharacterSpell> getCharacterSpells(Set<SpellDTO> dtos,
                                                    Character character) {
        return characterSpellMapper.fromDTOs(dtos, character);
    }

    @Override
    public CharacterDTO toDto(Character entity) {
        if (entity == null)
            return null;
        return new CharacterDTO(
                entity.getId().describeConstable(),
                entity.getIsDeleted(),
                entity.getName(),
                userMapper.toDto(entity.getUser()),
                classMapper.toDto(entity.getDNDclass()),
                entity.getLevel(),
                entity.getBaseStr(),
                entity.getBaseDex(),
                entity.getBaseCon(),
                entity.getBaseInt(),
                entity.getBaseWis(),
                entity.getBaseCha(),
                charProfMapper.toDTOs(entity.getProficiencyCharacters()),
                characterSpellMapper.toDTOs(entity.getCharacterSpells())
        );
    }


//    @Override
//    public List<Character> fromDTOs(List<CharacterDTO> characterDTOS, Role) {
//
//        return characterDTOS.stream()
//                .map(x->fr)
//                .toList();
//    }

    @Override
    public List<CharacterDTO> toDTOs(List<Character> characters) {

        return characters.stream()
                .map(this::toDto)
                .toList();
    }
}
