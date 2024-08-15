package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/characters")
public class CharacterController {
    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public List<CharacterDTO> getAll(){
        return service.getAll();
    }

    @PostMapping
    public void addCharacter(@RequestBody CharacterDTO createCharacterDTO){
        service.addCharacter(createCharacterDTO);
    }

    @PutMapping
    public void editCharacter(@RequestBody CharacterDTO saveCharacterDTO){
        service.editCharacter(saveCharacterDTO);
    }

    @DeleteMapping
    public void softDeleteCharacter(@RequestParam Long id){
        service.softDeleteCharacter(id);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public void hardDeleteCharacter(@RequestParam Long id){
        service.hardDeleteCharacter(id);
    }

    @GetMapping(path = "{characterId}")
    public CharacterDTO getCharacter(@PathVariable("characterId") Long id){
        return service.getCharacterById(id);
    }
}
