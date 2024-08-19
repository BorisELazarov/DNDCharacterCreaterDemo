package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.CharacterMapper;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.CharacterRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepo characterRepo;
    private final CharacterMapper mapper;

    public CharacterServiceImpl(CharacterRepo characterRepo, CharacterMapper mapper) {
        this.characterRepo = characterRepo;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<CharacterDTO>> getAll() {
        return new ResponseEntity<>(
                mapper.toDTOs(characterRepo.findAll()),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> addCharacter(CharacterDTO dto) {
        if (characterRepo.findByName(dto.name()).isPresent())
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        characterRepo.save(mapper.fromDto(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> editCharacter(CharacterDTO dto) {
        if (dto.id().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Character> foundCharacter = characterRepo.findByName(dto.name());

        if (foundCharacter.isPresent()
                && !foundCharacter.get().getId().equals(dto.id().orElse(null))) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        characterRepo.save(mapper.fromDto(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> softDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            character.get().setIsDeleted(true);
            characterRepo.save(character.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Void> hardDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            Character foundCharacter= character.get();
            if (foundCharacter.getIsDeleted()){
                characterRepo.delete(foundCharacter);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<CharacterDTO> getCharacterById(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        return character.map(value -> new ResponseEntity<>(
                mapper.toDto(value),
                HttpStatus.OK
        )).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


}
