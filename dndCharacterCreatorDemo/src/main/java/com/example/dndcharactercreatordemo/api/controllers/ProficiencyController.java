package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ProficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProficiencyDTO>> getProficiencies()
    {
        return proficiencyService.getProficiencies();
    }

    @GetMapping(path="{proficiencyId}")
    public ResponseEntity<ProficiencyDTO> getProficiency(@PathVariable("proficiencyId") Long id){
        return proficiencyService.getProficiency(id);
    }

    @PostMapping
    public ResponseEntity<Void> addProficiency(@RequestBody ProficiencyDTO proficiency){
        return proficiencyService.addProficiency(proficiency);
    }

    @PutMapping(path="{proficiencyId}")
    public ResponseEntity<Void> updateProficiency(
            @PathVariable("proficiencyId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type){
        return proficiencyService.updateProficiency(id,name,type);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteProficiency(@RequestParam Long id) {
         return proficiencyService.softDeleteProficiency(id);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteProficiency(@RequestParam Long id){
        return proficiencyService.hardDeleteProficiency(id);
    }
}
