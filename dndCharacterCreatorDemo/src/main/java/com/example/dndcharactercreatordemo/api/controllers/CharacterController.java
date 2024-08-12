package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CreateCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.ReadCharacterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.characters.SaveCharacterDTO;
import com.example.dndcharactercreatordemo.bll.services.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path = "/api/characters")
public class CharacterController {
    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReadCharacterDTO> getAll(){
        return service.getAll();
    }

    @PostMapping
    public void addCharacter(@RequestBody CreateCharacterDTO createCharacterDTO){
        service.addCharacter(createCharacterDTO);
    }

    @PutMapping
    public void editCharacter(@RequestBody SaveCharacterDTO saveCharacterDTO){
        service.editCharacter(saveCharacterDTO);
    }

    @DeleteMapping
    public void softDeleteCharacter(@RequestParam Long id){
        service.softDeleteCharacter(id);
    }

    @GetMapping(path = "{characterId}")
    public ReadCharacterDTO getCharacter(@PathVariable("characterId") Long id){
        return service.getCharacterById(id);
    }
}
