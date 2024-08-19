package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpellService {
    ResponseEntity<List<SpellDTO>> getSpells();

    ResponseEntity<SpellDTO> getSpell(Long id);

    ResponseEntity<Void> addSpell(SpellDTO spellDTO);

    ResponseEntity<Void> editSpell(SpellDTO spellDTO, Long id);

    ResponseEntity<Void> softDeleteSpell(Long id);

    ResponseEntity<Void> hardDeleteSpell(Long id);
}
