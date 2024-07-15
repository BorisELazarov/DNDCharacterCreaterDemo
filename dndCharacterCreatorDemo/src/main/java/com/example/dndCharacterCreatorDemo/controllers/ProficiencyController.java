package com.example.dndCharacterCreatorDemo.controllers;

import com.example.dndCharacterCreatorDemo.entities.Proficiency;
import com.example.dndCharacterCreatorDemo.services.ProficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/proficiencies")
public class ProficiencyController {
    private final ProficiencyService proficiencyService;

    @Autowired
    public ProficiencyController(ProficiencyService proficiencyService) {
        this.proficiencyService = proficiencyService;
    }

    @GetMapping
    public List<Proficiency> getProficiencies()
    {
        return proficiencyService.getProficiencies();
    }

    @GetMapping(path="{proficiencyId}")
    public Proficiency getProficiency(@PathVariable("proficiencyId") Long id){
        return proficiencyService.getProficiency(id);
    }

    @PostMapping
    public void addProficiency(@RequestBody Proficiency proficiency){
        proficiencyService.addProficiency(proficiency);
    }

    @PutMapping(path="{proficiencyId}")
    public void updateProficiency(
            @PathVariable("proficiencyId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type){
        proficiencyService.updateProficiency(id,name,type);
    }

    @DeleteMapping(path="{proficiencyId}")
    public void deleteProficiency(@PathVariable("proficiencyId") Long id) {
        proficiencyService.deleteProficiency(id);
    }
}
