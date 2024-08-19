package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CharacterService {
    ResponseEntity<List<CharacterDTO>> getAll();
    ResponseEntity<Void> addCharacter(CharacterDTO dto);
    ResponseEntity<Void> editCharacter(CharacterDTO dto);
    ResponseEntity<Void> softDeleteCharacter(Long id);
    ResponseEntity<CharacterDTO> getCharacterById(Long id);

    ResponseEntity<Void> hardDeleteCharacter(Long id);
}
