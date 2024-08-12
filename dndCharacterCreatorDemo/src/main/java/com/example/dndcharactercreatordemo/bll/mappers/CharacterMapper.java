package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.CreateCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.ReadCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.SaveCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.CreateSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.ReadSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SaveSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.CreateUserDTO;
import com.example.dndcharactercreatordemo.dal.entities.*;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.ClassRepo;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterMapper implements IMapper<CreateCharacterDTO, SaveCharacterDTO, ReadCharacterDTO, Character> {
    private final UserRepo userRepo;
    private final ClassRepo classRepo;
    private final ProficiencyRepo proficiencyRepo;
    private final SpellRepo spellRepo;

    public CharacterMapper(UserRepo userRepo, ClassRepo classRepo, ProficiencyRepo proficiencyRepo, SpellRepo spellRepo) {
        this.userRepo = userRepo;
        this.classRepo = classRepo;
        this.proficiencyRepo = proficiencyRepo;
        this.spellRepo = spellRepo;
    }

    @Override
    public Character fromCreateDto(CreateCharacterDTO dto) {
        if (dto.name() == null)
            throw new IllegalArgumentException("Character's name must not be null");

        Character character = new Character();
        character.setIsDeleted(dto.isDeleted());
        character.setName(dto.name());
        character.setLevel(dto.level());
        character.setBaseStr(dto.baseStr());
        character.setBaseDex(dto.baseDex());
        character.setBaseCon(dto.baseCon());
        character.setBaseInt(dto.baseInt());
        character.setBaseWis(dto.baseWis());
        character.setBaseCha(dto.baseCha());

        User user = userRepo.findByUsername(dto.user());
        if (user == null) {
            throw new IllegalArgumentException("There is no such user");
        }
        character.setUser(user);

        DNDclass dnDclass = classRepo.findByName(dto.dndClass());
        if (dnDclass == null)
            throw new IllegalArgumentException("There is no such dnd class");
        character.setDNDclass(dnDclass);

        character.setProficiencyCharacters(new LinkedList<>());
        for (CharacterProficiencyDTO proficiencyDTO : dto.proficiencies()) {
            Proficiency proficiency = proficiencyRepo.findByName(proficiencyDTO.name());
            if (proficiency.getId() == null)
                throw new IllegalArgumentException("There is no such proficiency");
            ProficiencyCharacter proficiencyCharacter = new ProficiencyCharacter();
            proficiencyCharacter.setExpertise(proficiencyDTO.expertise());
            proficiencyCharacter.setId(new ProficiencyCharacterPairId());
            proficiencyCharacter.getId().setCharacter(character);
            proficiencyCharacter.getId().setProficiency(proficiency);
            character.getProficiencyCharacters().add(proficiencyCharacter);
        }

        character.setCharacterSpells(new LinkedList<>());
        for (String spellName : dto.spells()) {
            Spell spell = spellRepo.findByName(spellName);
            CharacterSpell characterSpell = new CharacterSpell();
            characterSpell.setId(new CharacterSpellPairId());
            characterSpell.getId().setSpell(spell);
            characterSpell.getId().setCharacter(character);
            character.getCharacterSpells().add(characterSpell);
        }

        return character;
    }

    @Override
    public Character fromSaveDto(SaveCharacterDTO dto) {
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
        User user = userRepo.findByUsername(dto.user());
        if (user == null)
            throw new IllegalArgumentException("There is no such user");
        character.setUser(user);
        DNDclass dnDclass = classRepo.findByName(dto.dndClass());
        if (dnDclass == null)
            throw new IllegalArgumentException("There is no such dnd class");
        character.setDNDclass(dnDclass);
        character.setProficiencyCharacters(new LinkedList<>());
        for (CharacterProficiencyDTO proficiencyDTO : dto.proficiencies()) {
            Proficiency proficiency = proficiencyRepo.findByName(proficiencyDTO.name());
            if (proficiency.getId() == null)
                throw new IllegalArgumentException("There is no such proficiency");
            ProficiencyCharacter proficiencyCharacter = new ProficiencyCharacter();
            proficiencyCharacter.setExpertise(proficiencyDTO.expertise());
            proficiencyCharacter.setId(new ProficiencyCharacterPairId());
            proficiencyCharacter.getId().setCharacter(character);
            proficiencyCharacter.getId().setProficiency(proficiency);
            character.getProficiencyCharacters().add(proficiencyCharacter);
        }
        character.setCharacterSpells(new LinkedList<>());
        for (String spellName : dto.spells()) {
            Spell spell = spellRepo.findByName(spellName);
            CharacterSpell characterSpell = new CharacterSpell();
            characterSpell.setId(new CharacterSpellPairId());
            characterSpell.getId().setSpell(spell);
            characterSpell.getId().setCharacter(character);
            character.getCharacterSpells().add(characterSpell);
        }
        return character;
    }

    @Override
    public ReadCharacterDTO toDto(Character entity) {
        if (entity == null)
            return null;
        return new ReadCharacterDTO(
                entity.getId(),
                entity.getIsDeleted(),
                entity.getName(),
                entity.getUser().getUsername(),
                entity.getDNDclass().getName(),
                entity.getLevel(),
                entity.getBaseStr(),
                entity.getBaseDex(),
                entity.getBaseCon(),
                entity.getBaseInt(),
                entity.getBaseWis(),
                entity.getBaseCha(),
                entity.getProficiencyCharacters().stream()
                        .map(x -> new CharacterProficiencyDTO(
                                x.getId().getProficiency().getName(),
                                x.isExpertise()
                        )).collect(Collectors.toSet()),
                entity.getCharacterSpells().stream()
                        .map(CharacterSpell::getId)
                        .map(CharacterSpellPairId::getSpell)
                        .map(Spell::getName)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public List<Character> fromCreateDTOs(List<CreateCharacterDTO> characterDTOS) {
        return characterDTOS.stream()
                .map(this::fromCreateDto)
                .toList();
    }

    @Override
    public List<Character> fromSaveDTOs(List<SaveCharacterDTO> characterDTOS) {

        return characterDTOS.stream()
                .map(this::fromSaveDto)
                .toList();
    }

    @Override
    public List<ReadCharacterDTO> toDTOs(List<Character> characters) {

        return characters.stream()
                .map(this::toDto)
                .toList();
    }
}
