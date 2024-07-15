package com.example.dndCharacterCreatorDemo.controllers;

import com.example.dndCharacterCreatorDemo.entities.DNDclass;
import com.example.dndCharacterCreatorDemo.services.ClassService;
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
    public List<DNDclass> getClasses()
    {
        return classService.getClasses();
    }

    @GetMapping(path="{classId}")
    public DNDclass getClass(@PathVariable("classId") Long id){
        return classService.getClass(id);
    }

    @PostMapping
    public void addClass(@RequestBody DNDclass dndClass){
        classService.addClass(dndClass);
    }

    @PutMapping(path="{classId}")
    public void updateClass(
            @PathVariable("classId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer hitDice){
        classService.updateClass(id,name,description,hitDice);
    }

    @DeleteMapping(path="{classId}")
    public void deleteClass(@PathVariable("classId") Long id) {
        classService.deleteClass(id);
    }
}
