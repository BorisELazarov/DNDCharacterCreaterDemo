package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/spells")
public class SpellController {
    private final SpellService spellService;

    @Autowired
    public SpellController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping
    public ResponseEntity<List<SpellDTO>> getSpells(@RequestParam Optional<String> name,
                                                    @RequestParam Optional<Byte> level,
                                                    @RequestParam Optional<String> castingTime,
                                                    @RequestParam Optional<Integer> range,
                                                    @RequestParam Optional<String> sortBy,
                                                    @RequestParam(defaultValue = "true") boolean ascending){
        return new ResponseEntity<>(
                spellService.getSpells(false, name, level, castingTime,
                        range, sortBy, ascending),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/deleted")
    public ResponseEntity<List<SpellDTO>> getDeletedSpells(@RequestParam Optional<String> name,
                                                           @RequestParam Optional<Byte> level,
                                                           @RequestParam Optional<String> castingTime,
                                                           @RequestParam Optional<Integer> range,
                                                           @RequestParam Optional<String> sortBy,
                                                           @RequestParam(defaultValue = "true") boolean ascending){
        return new ResponseEntity<>(
                spellService.getSpells(true, name, level, castingTime,
                        range, sortBy, ascending),
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
    public ResponseEntity<SpellDTO> postSpell(@RequestBody @Valid SpellDTO spell){
        return new ResponseEntity<>(
                spellService.addSpell(spell),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/{spellId}")
    public ResponseEntity<SpellDTO> putSpell(@RequestBody @Valid SpellDTO spell,
                         @PathVariable("spellId") Long id){
        return new ResponseEntity<>(
                spellService.editSpell(spell),
                HttpStatus.OK
        );
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
