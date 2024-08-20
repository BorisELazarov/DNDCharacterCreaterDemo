package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.CharacterMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.CharacterRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private static final String NOT_FOUND_MESSAGE="The character is not found!";
    private static final String NAME_TAKEN_MESSAGE="There is already character named like that!";
    private final CharacterRepo characterRepo;
    private final CharacterMapper mapper;

    public CharacterServiceImpl(CharacterRepo characterRepo, CharacterMapper mapper) {
        this.characterRepo = characterRepo;
        this.mapper = mapper;
    }

    @Override
    public List<CharacterDTO> getAll() {
        return mapper.toDTOs(characterRepo.findAll());
    }

    @Override
    public void addCharacter(CharacterDTO dto) {
        if (characterRepo.findByName(dto.name()).isPresent())
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        characterRepo.save(mapper.fromDto(dto));
    }

    @Override
    @Transactional
    public void editCharacter(CharacterDTO dto) {
        if (dto.id().isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);

        }

        Optional<Character> foundCharacter = characterRepo.findByName(dto.name());

        if (foundCharacter.isPresent()
                && !foundCharacter.get().getId().equals(dto.id().orElse(null))) {
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }

        characterRepo.save(mapper.fromDto(dto));
    }

    @Override
    public void softDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        } else {
            character.get().setIsDeleted(true);
            characterRepo.save(character.get());
        }
    }

    @Override
    public void hardDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);

        }
        else {
            Character foundCharacter= character.get();
            if (foundCharacter.getIsDeleted()){
                characterRepo.delete(foundCharacter);
            }
            else {
                throw new NotSoftDeletedException("The character must be soft deleted first!");
            }
        }
    }

    @Override
    public CharacterDTO getCharacterById(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isPresent()){
            return mapper.toDto(character.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }


}
