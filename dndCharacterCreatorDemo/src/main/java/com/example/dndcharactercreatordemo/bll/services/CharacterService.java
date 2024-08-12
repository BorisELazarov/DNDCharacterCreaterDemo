package com.example.dndcharactercreatordemo.bll.services;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CreateCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.ReadCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.SaveCharacterDTO;
import com.example.dndcharactercreatordemo.bll.mappers.CharacterMapper;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {
    private final CharacterRepo characterRepo;
    private final IMapper<CreateCharacterDTO, SaveCharacterDTO, ReadCharacterDTO, Character> mapper;

    public CharacterService(CharacterRepo characterRepo, UserRepo userRepo,
                            ClassRepo classRepo, ProficiencyRepo proficiencyRepo,
                            SpellRepo spellRepo) {
        this.characterRepo = characterRepo;
        this.mapper = new CharacterMapper(userRepo, classRepo, proficiencyRepo, spellRepo);
    }

    public List<ReadCharacterDTO> getAll(){
        return mapper.toDTOs(characterRepo.findAll());
    }

    public void addCharacter(CreateCharacterDTO dto) {
        if (characterRepo.findByName(dto.name())!=null)
            throw new IllegalArgumentException("There is already character with such name!");
        characterRepo.save(mapper.fromCreateDto(dto));

    }

    public void editCharacter(SaveCharacterDTO dto) {
        Long id=null;
        if (dto.id().isEmpty()){
            noCharacterException();
        }
        else {
            id=dto.id().get();
        }
        Character character=characterRepo.findByName(dto.name());
        if (character!=null && !character.getId().equals(id)){
            throw new IllegalArgumentException("There is already character with such name!");
        }
        characterRepo.save(mapper.fromSaveDto(dto));
    }

    public void softDeleteCharacter(Long id) {
        Optional<Character> character=characterRepo.findById(id);
        if (character.isEmpty()){
            noCharacterException();
        }
        else
        {
            character.get().setIsDeleted(true);
            characterRepo.save(character.get());
        }
    }

    public ReadCharacterDTO getCharacterById(Long id) {
        Optional<Character> character=characterRepo.findById(id);
        if (character.isEmpty()){
            noCharacterException();
        }
        return mapper.toDto(character.get());
    }

    private void noCharacterException(){
        throw new IllegalArgumentException("There is no such character!");
    }
}
