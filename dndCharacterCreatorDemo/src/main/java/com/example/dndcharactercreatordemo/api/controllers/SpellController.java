package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.SpellDTO;
import com.example.dndcharactercreatordemo.bll.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/spells")
public class SpellController {
    private final SpellService service;

    @Autowired
    public SpellController(SpellService service) {
        this.service = service;
    }

    @GetMapping
    public List<SpellDTO> getSpells(){
        return service.getSpells();
    }

    @GetMapping(path="{spellId}")
    public SpellDTO getSpell(@PathVariable("spellId") Long id) {
        return service.getSpell(id);
    }

    @PostMapping
    public void postSpell(@RequestBody SpellDTO spell){
        service.addSpell(spell);
    }

    @PutMapping
    public void putSpell(@RequestBody SpellDTO spell){
        service.editSpell(spell);
    }

    @DeleteMapping(path="{spellId}")
    public void deleteSpell(@PathVariable("spellId") Long id) {
        service.softDeleteSpell(id);
    }
}
