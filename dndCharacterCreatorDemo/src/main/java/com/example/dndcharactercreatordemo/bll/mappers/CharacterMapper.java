package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.dal.entities.*;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.ClassRepo;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CharacterMapper implements IMapper<CharacterDTO, Character> {
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
        Optional<User> user = userRepo.findByUsername(dto.user());
        if (user.isEmpty())
            throw new IllegalArgumentException("There is no such user");
        character.setUser(user.get());
        Optional<DNDclass> dnDclass = classRepo.findByName(dto.dndClass());
        if (dnDclass.isEmpty())
            throw new IllegalArgumentException("There is no such dnd class");
        character.setDNDclass(dnDclass.get());
        character.setProficiencyCharacters(new LinkedList<>());
        for (CharacterProficiencyDTO proficiencyDTO : dto.proficiencies()) {
            Optional<Proficiency> proficiency = proficiencyRepo.findByName(proficiencyDTO.name());
            if (proficiency.isEmpty())
                throw new IllegalArgumentException("There is no such proficiency");
            ProficiencyCharacter proficiencyCharacter = new ProficiencyCharacter();
            proficiencyCharacter.setExpertise(proficiencyDTO.expertise());
            proficiencyCharacter.setId(new ProficiencyCharacterPairId());
            proficiencyCharacter.getId().setCharacter(character);
            proficiencyCharacter.getId().setProficiency(proficiency.get());
            character.getProficiencyCharacters().add(proficiencyCharacter);
        }
        character.setCharacterSpells(new LinkedList<>());
        for (String spellName : dto.spells()) {
            Optional<Spell> spell = spellRepo.findByName(spellName);
            CharacterSpell characterSpell = new CharacterSpell();
            characterSpell.setId(new CharacterSpellPairId());
            characterSpell.getId().setSpell(spell.get());
            characterSpell.getId().setCharacter(character);
            character.getCharacterSpells().add(characterSpell);
        }
        return character;
    }

    @Override
    public CharacterDTO toDto(Character entity) {
        if (entity == null)
            return null;
        return new CharacterDTO(
                entity.getId().describeConstable(),
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
