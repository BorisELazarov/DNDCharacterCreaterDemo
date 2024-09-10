package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ClassService;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="api/classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public ResponseEntity<List<ClassDTO>> getClasses(
            @RequestParam Optional<String> name,
            @RequestParam Optional<HitDiceEnum> hitDice,
            @RequestParam Optional<String> sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
            )
    {
        return new ResponseEntity<>(
            classService.getClasses(false, name, hitDice, sortBy, ascending),
            HttpStatus.OK
        );
    }

    @GetMapping(path = "/deleted")
    public ResponseEntity<List<ClassDTO>> getDeletedClasses(
            @RequestParam Optional<String> name,
            @RequestParam Optional<HitDiceEnum> hitDice,
            @RequestParam Optional<String> sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    )
    {
        return new ResponseEntity<>(
                classService.getClasses(true, name, hitDice, sortBy, ascending),
                HttpStatus.OK
        );
    }

    @GetMapping(path="{classId}")
    public ResponseEntity<ClassDTO> getClass(@PathVariable("classId") Long id){

        return new ResponseEntity<>(
                classService.getClassById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ClassDTO> addClass(@Valid @RequestBody ClassDTO classDTO){
        return new ResponseEntity<>(
                classService.addClass(classDTO),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClassDTO> updateClass(
            @RequestBody ClassDTO classDTO){
        return new ResponseEntity<>(
                classService.updateClass(classDTO),
                HttpStatus.OK);
    }

    @PutMapping(path="{classId}")
    public ResponseEntity<Void> restoreClass(
            @PathVariable("classId") Long id){
        classService.restoreClass(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteClass(@RequestParam Long id) {
        classService.softDeleteClass(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path="/confirmedDelete")
    public ResponseEntity<Void> hardDeleteClass(@RequestParam Long id) {
        classService.hardDeleteClass(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
