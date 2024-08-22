package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ProficiencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(
                proficiencyService.getProficiencies(false),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/deleted")
    public ResponseEntity<List<ProficiencyDTO>> getDeletedProficiencies()
    {
        return new ResponseEntity<>(
                proficiencyService.getProficiencies(true),
                HttpStatus.OK
        );
    }

    @GetMapping(path="{proficiencyId}")
    public ResponseEntity<ProficiencyDTO> getProficiency(@PathVariable("proficiencyId") Long id){
        return new ResponseEntity<>(
                proficiencyService.getProficiency(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Void> addProficiency(@RequestBody @Valid ProficiencyDTO proficiency){
        proficiencyService.addProficiency(proficiency);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path="{proficiencyId}")
    public ResponseEntity<Void> updateProficiency(
            @PathVariable("proficiencyId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type){
        proficiencyService.updateProficiency(id,name,type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path="/restore/{proficiencyId}")
    public ResponseEntity<Void> restoreProficiency(
            @PathVariable("proficiencyId") Long id){
        proficiencyService.restoreProficiency(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteProficiency(@RequestParam Long id) {
        proficiencyService.softDeleteProficiency(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteProficiency(@RequestParam Long id){
        proficiencyService.hardDeleteProficiency(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
