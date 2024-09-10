package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;

import java.util.List;
import java.util.Optional;

public interface CharacterService {
    List<CharacterDTO> getCharacters(boolean isDeleted, Optional<String> username,
                                     Optional<Byte> level, Optional<String> roleTitle,
                                     Optional<String> sortBy, boolean ascending);
    CharacterDTO addCharacter(CharacterDTO dto);
    void restoreCharacter(Long id);
    CharacterDTO editCharacter(CharacterDTO dto);
    void softDeleteCharacter(Long id);
    CharacterDTO getCharacterById(Long id);

    void hardDeleteCharacter(Long id);
}
