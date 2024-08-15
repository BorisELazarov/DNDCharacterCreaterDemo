package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ClassService;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ClassDTO> getClasses()
    {
        return classService.getClasses();
    }

    @GetMapping(path="{classId}")
    public ClassDTO getClass(@PathVariable("classId") Long id){
        return classService.getClass(id);
    }

    @PostMapping
    public void addClass(@RequestBody ClassDTO dndClass){
        classService.addClass(dndClass);
    }

    @PutMapping(path="{classId}")
    public void updateClass(
            @PathVariable("classId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) HitDiceEnum hitDice){
        classService.updateClass(id,name,description,hitDice);
    }

    @DeleteMapping
    public void softDeleteClass(@RequestParam Long id) {
        classService.softDeleteClass(id);
    }

    @DeleteMapping(path="/confirmedDelete")
    public void hardDeleteClass(@RequestParam Long id) {
        classService.hardDeleteClass(id);
    }
}
