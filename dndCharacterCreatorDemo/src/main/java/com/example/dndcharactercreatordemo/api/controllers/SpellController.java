package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(
                spellService.getSpells(false),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/deleted")
    public ResponseEntity<List<SpellDTO>> getDeletedSpells(){
        return new ResponseEntity<>(
                spellService.getSpells(true),
                HttpStatus.OK
        );
    }

    @GetMapping(path="/{spellId}")
    public ResponseEntity<SpellDTO> getSpell(@PathVariable("spellId") Long id) {
        return new ResponseEntity<>(
                spellService.getSpell(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Void> postSpell(@RequestBody @Valid SpellDTO spell){
        spellService.addSpell(spell);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{spellId}")
    public ResponseEntity<Void> putSpell(@RequestBody @Valid SpellDTO spell,
                         @PathVariable("spellId") Long id){
        spellService.editSpell(spell, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/restore/{spellId}")
    public ResponseEntity<Void> restoreSpell(@PathVariable("spellId") Long id){
        spellService.restoreSpell(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteSpell(@RequestParam Long id) {
        spellService.softDeleteSpell(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteSpell(@RequestParam Long id){
        spellService.hardDeleteSpell(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
