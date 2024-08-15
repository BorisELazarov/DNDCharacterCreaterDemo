package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ProficiencyService;
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
    public List<ProficiencyDTO> getProficiencies()
    {
        return proficiencyService.getProficiencies();
    }

    @GetMapping(path="{proficiencyId}")
    public ProficiencyDTO getProficiency(@PathVariable("proficiencyId") Long id){
        return proficiencyService.getProficiency(id);
    }

    @PostMapping
    public void addProficiency(@RequestBody ProficiencyDTO proficiency){
        proficiencyService.addProficiency(proficiency);
    }

    @PutMapping(path="{proficiencyId}")
    public void updateProficiency(
            @PathVariable("proficiencyId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type){
        proficiencyService.updateProficiency(id,name,type);
    }

    @DeleteMapping
    public void softDeleteProficiency(@RequestParam Long id) {
        proficiencyService.softDeleteProficiency(id);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public void hardDeleteProficiency(@RequestParam Long id){
        proficiencyService.hardDeleteProficiency(id);
    }
}
