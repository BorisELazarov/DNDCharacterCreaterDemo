package com.example.dndcharactercreatordemo.bll.dtos.characters;

import java.util.Optional;
import java.util.Set;

public record CharacterDTO(
        Optional<Long> id, Boolean isDeleted,
        String name, String user,
        String dndClass, byte level,
        byte baseStr, byte baseDex,
        byte baseCon, byte baseInt,
        byte baseWis, byte baseCha,
        Set<CharacterProficiencyDTO> proficiencies,
        Set<String> spells
) {
    public CharacterDTO{
        proficiencies=Set.copyOf(proficiencies);
        spells=Set.copyOf(spells);
    }
}
