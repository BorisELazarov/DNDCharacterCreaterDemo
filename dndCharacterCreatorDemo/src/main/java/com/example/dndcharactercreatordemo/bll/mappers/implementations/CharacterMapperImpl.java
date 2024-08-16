package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.ProficiencyCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.dal.entities.*;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapper implements IMapper<CharacterDTO, Character> {
    private final IMapper<UserDTO, User> userMapper;
    private final IMapper<ClassDTO, DNDclass> classMapper;
    private final IMapper<ProficiencyDTO, Proficiency> proficiencyMapper;
    private final IMapper<SpellDTO, Spell> spellMapper;
    private final IMapper<ProficiencyCharacterDTO, ProficiencyCharacter> charProfMapper;

    public CharacterMapper(IMapper<UserDTO, User> userMapper,
                           IMapper<ClassDTO, DNDclass> classMapper,
                           IMapper<ProficiencyDTO, Proficiency> proficiencyMapper,
                           IMapper<SpellDTO, Spell> spellMapper,
                           IMapper<ProficiencyCharacterDTO, ProficiencyCharacter> charProfMapper) {
        this.userMapper = userMapper;
        this.classMapper = classMapper;
        this.proficiencyMapper = proficiencyMapper;
        this.spellMapper = spellMapper;
        this.charProfMapper = charProfMapper;
    }

    @Override
    public Character fromDto(CharacterDTO dto) {
        if (dto.name() == null)
            throw new IllegalArgumentException("Character's name must not be null");
        Character character = new Character();
        if (dto.id().isPresent())
            character.setId(dto.id().get());
        character.setIsDeleted(dto.isDeleted());
        character.setName(dto.name());
        character.setLevel(dto.level());
        character.setBaseStr(dto.baseStr());
        character.setBaseDex(dto.baseDex());
        character.setBaseCon(dto.baseCon());
        character.setBaseInt(dto.baseInt());
        character.setBaseWis(dto.baseWis());
        character.setBaseCha(dto.baseCha());
        character.setUser(userMapper.fromDto(dto.user()));
        character.setDNDclass(classMapper.fromDto(dto.dndClass()));
        character.setProficiencyCharacters(getProficiencyCharacters(dto.proficiencies(), character));
        character.setCharacterSpells(getCharacterSpells(dto.spells(),character));
        return character;
    }

    private List<ProficiencyCharacter> getProficiencyCharacters(Set<ProficiencyCharacterDTO> dtos,
                                                                Character character) {
        List<ProficiencyCharacter> proficiencyCharacters = charProfMapper
                .fromDTOs(dtos.stream().toList());
        for (ProficiencyCharacter proficiencyCharacter : proficiencyCharacters) {
            proficiencyCharacter.getId().setCharacter(character);
        }
        return proficiencyCharacters;
    }

    private List<CharacterSpell> getCharacterSpells(Set<SpellDTO> dtos,
                                                    Character character) {
        List<Spell> spells = spellMapper.fromDTOs(dtos.stream().toList());
        List<CharacterSpell> characterSpells = new LinkedList<>();
        for (Spell spell : spells) {
            characterSpells.add(
                    getCharacterSpell(spell, character)
            );
        }
        return characterSpells;
    }

    private CharacterSpell getCharacterSpell(Spell spell, Character character) {
        CharacterSpell characterSpell = new CharacterSpell();
        CharacterSpellPairId id = new CharacterSpellPairId();
        id.setCharacter(character);
        id.setSpell(spell);
        characterSpell.setId(id);
        return characterSpell;
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
                new HashSet<>(charProfMapper.toDTOs(entity.getProficiencyCharacters())),
                getSpells(entity.getCharacterSpells())
        );
    }

    private Set<ProficiencyDTO> getProficiencies(List<ProficiencyCharacter> proficiencyCharacters) {
        List<Proficiency> proficiencies = proficiencyCharacters
                .stream()
                .map(ProficiencyCharacter::getId)
                .map(ProficiencyCharacterPairId::getProficiency)
                .toList();
        return new HashSet<>(proficiencyMapper.toDTOs(proficiencies));
    }

    private Set<SpellDTO> getSpells(List<CharacterSpell> characterSpells) {
        List<Spell> spells = characterSpells
                .stream().map(CharacterSpell::getId)
                .map(CharacterSpellPairId::getSpell)
                .toList();
        return new HashSet<>(spellMapper.toDTOs(spells));
    }


    @Override
    public List<Character> fromDTOs(List<CharacterDTO> characterDTOS) {

        return characterDTOS.stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<CharacterDTO> toDTOs(List<Character> characters) {

        return characters.stream()
                .map(this::toDto)
                .toList();
    }
}
