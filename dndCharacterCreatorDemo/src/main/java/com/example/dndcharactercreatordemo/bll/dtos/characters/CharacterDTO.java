package com.example.dndcharactercreatordemo.bll.dtos.characters;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;

import java.util.Optional;
import java.util.Set;

public record CharacterDTO(
        Optional<Long> id, Boolean isDeleted,
        String name, UserDTO user,
        ClassDTO dndClass, byte level,
        byte baseStr, byte baseDex,
        byte baseCon, byte baseInt,
        byte baseWis, byte baseCha,
        Set<ProficiencyCharacterDTO> proficiencies,
        Set<SpellDTO> spells
) {
    public CharacterDTO{
        proficiencies=Set.copyOf(proficiencies);
        spells=Set.copyOf(spells);
    }
}
