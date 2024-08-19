package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/spells")
public class SpellController {
    private final SpellService spellService;

    @Autowired
    public SpellController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping
    public ResponseEntity<List<SpellDTO>> getSpells(){
        return spellService.getSpells();
    }

    @GetMapping(path="/{spellId}")
    public ResponseEntity<SpellDTO> getSpell(@PathVariable("spellId") Long id) {
        return spellService.getSpell(id);
    }

    @PostMapping
    public ResponseEntity<Void> postSpell(@RequestBody SpellDTO spell){
        return spellService.addSpell(spell);
    }

    @PutMapping(path = "/{spellId}")
    public ResponseEntity<Void> putSpell(@RequestBody SpellDTO spell,
                         @PathVariable("spellId") Long id){
        return spellService.editSpell(spell, id);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteSpell(@RequestParam Long id) {
        return spellService.softDeleteSpell(id);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteSpell(@RequestParam Long id){

        return spellService.hardDeleteSpell(id);
    }
}
