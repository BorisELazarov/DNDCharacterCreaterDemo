package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<SpellDTO> getSpells(){
        return spellService.getSpells();
    }

    @GetMapping(path="/{spellId}")
    public SpellDTO getSpell(@PathVariable("spellId") Long id) {
        return spellService.getSpell(id);
    }

    @PostMapping
    public void postSpell(@RequestBody SpellDTO spell){
        spellService.addSpell(spell);
    }

    @PutMapping(path = "/{spellId}")
    public void putSpell(@RequestBody SpellDTO spell,
                         @PathVariable("spellId") Long id){
        spellService.editSpell(spell, id);
    }

    @DeleteMapping
    public void softDeleteSpell(@RequestParam Long id) {
        spellService.softDeleteSpell(id);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public void hardDeleteSpell(@RequestParam Long id){
        spellService.hardDeleteSpell(id);
    }
}
