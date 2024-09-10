package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;

import java.util.List;
import java.util.Optional;

public interface SpellService {
    List<SpellDTO> getSpells(boolean isDeleted,
                             Optional<String> name,
                             Optional<Byte> level,
                             Optional<String> castingTime,
                             Optional<Integer> range,
                             Optional<String> sortBy,
                             boolean ascending);

    SpellDTO getSpell(Long id);

    SpellDTO addSpell(SpellDTO spellDTO);

    SpellDTO editSpell(SpellDTO spellDTO);

    void softDeleteSpell(Long id);

    void hardDeleteSpell(Long id);

    void restoreSpell(Long id);
}
