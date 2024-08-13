package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.CharacterRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepo characterRepo;
    private final IMapper<CharacterDTO, Character> mapper;

    public CharacterServiceImpl(CharacterRepo characterRepo, IMapper<CharacterDTO, Character> mapper) {
        this.characterRepo = characterRepo;
        this.mapper = mapper;
    }

    @Override
    public List<CharacterDTO> getAll() {
        return mapper.toDTOs(characterRepo.findAll());
    }

    @Override
    public void addCharacter(CharacterDTO dto) {
        if (characterRepo.findByName(dto.name()) != null)
            throw new IllegalArgumentException("There is already character with such name!");
        characterRepo.save(mapper.fromDto(dto));

    }

    @Override
    public void editCharacter(CharacterDTO dto) {
        if (dto.id().isEmpty()) {
            noCharacterException();
        }

        Character character = characterRepo.findByName(dto.name());

        if (character != null && !character.getId().equals(dto.id().orElse(null))) {
            throw new IllegalArgumentException("There is already character with such name!");
        }

        characterRepo.save(mapper.fromDto(dto));
    }

    @Override
    public void softDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            noCharacterException();
        } else {
            character.get().setIsDeleted(true);
            characterRepo.save(character.get());
        }
    }

    @Override
    public CharacterDTO getCharacterById(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            noCharacterException();
        }

        return mapper.toDto(character.get());
    }

    private void noCharacterException() {
        throw new IllegalArgumentException("There is no such character!");
    }
}
