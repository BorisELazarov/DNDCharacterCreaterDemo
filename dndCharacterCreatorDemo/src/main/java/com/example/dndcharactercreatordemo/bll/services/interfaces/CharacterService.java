package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;

import java.util.List;

public interface CharacterService {
    List<CharacterDTO> getAll();
    void addCharacter(CharacterDTO dto);
    void editCharacter(CharacterDTO dto);
    void softDeleteCharacter(Long id);
    CharacterDTO getCharacterById(Long id);

    void hardDeleteCharacter(Long id);
}
