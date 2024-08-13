package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;

import java.util.List;

public interface SpellService {
    List<SpellDTO> getSpells();

    SpellDTO getSpell(Long id);

    void addSpell(SpellDTO spellDTO);

    void editSpell(SpellDTO spellDTO, Long id);

    void softDeleteSpell(Long id);
}
