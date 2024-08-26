package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.CharacterMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.CharacterRepo;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private static final String NOT_FOUND_MESSAGE = "The character is not found!";
    private static final String NAME_TAKEN_MESSAGE = "There is already character named like that!";
    private final RoleRepo roleRepo;
    private final CharacterRepo characterRepo;
    private final CharacterMapper mapper;

    public CharacterServiceImpl(@NotNull CharacterRepo characterRepo,
                                @NotNull CharacterMapper mapper,
                                @NotNull RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
        this.characterRepo = characterRepo;
        this.mapper = mapper;
    }

    @Override
    public List<CharacterDTO> getCharacters(boolean isDeleted) {
        return mapper.toDTOs(characterRepo.findAll(isDeleted));
    }

    @Override
    public CharacterDTO addCharacter(CharacterDTO dcharacterDTO) {
        if (characterRepo.findByName(dcharacterDTO.name()).isPresent())
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        Character character = characterRepo.save(mapper.fromDto(
                dcharacterDTO,
                roleRepo.findByTitle(dcharacterDTO.user().role())
        ));
        return mapper.toDto(character);
    }

    @Override
    @Transactional
    public CharacterDTO editCharacter(CharacterDTO characterDTO) {
        Optional<Long> optionalId = characterDTO.id();
        if (optionalId.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Long id = optionalId.get();
        Optional<Character> character = characterRepo.findById(id);
        if (character.isPresent()) {
            characterRepo.findByName(characterDTO.name()).ifPresent(
                    x -> {
                        if (!x.getId().equals(id)) {
                            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                        }
                    }
            );
            characterRepo.save(character.get());
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return characterDTO;
    }

    @Override
    public void restoreCharacter(Long id) {
        Optional<Character> optionalCharacter = characterRepo.findById(id);
        if (optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            characterRepo.findByName(character.getName())
                    .ifPresent(x -> {
                        throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                    });
            character.setIsDeleted(false);
            characterRepo.save(character);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
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

        } else {
            Character foundCharacter = character.get();
            if (foundCharacter.getIsDeleted()) {
                characterRepo.delete(foundCharacter);
            } else {
                throw new NotSoftDeletedException("The character must be soft deleted first!");
            }
        }
    }

    @Override
    public CharacterDTO getCharacterById(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isPresent()) {
            return mapper.toDto(character.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }


}
