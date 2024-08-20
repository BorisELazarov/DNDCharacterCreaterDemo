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

@RestController
@RequestMapping(path="api/classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public ResponseEntity<List<ClassDTO>> getClasses()
    {
        return new ResponseEntity<>(
            classService.getClasses(),
            HttpStatus.OK
        );
    }

    @GetMapping(path="{classId}")
    public ResponseEntity<ClassDTO> getClass(@PathVariable("classId") Long id){

        return new ResponseEntity<>(
                classService.getClass(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Void> addClass(@RequestBody @Valid ClassDTO dndClass){
        classService.addClass(dndClass);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path="{classId}")
    public ResponseEntity<Void> updateClass(
            @PathVariable("classId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) HitDiceEnum hitDice){
        classService.updateClass(id,name,description,hitDice);
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
