package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CharacterDTO>> getAll(){
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Void> addCharacter(@RequestBody CharacterDTO createCharacterDTO){
        return service.addCharacter(createCharacterDTO);
    }

    @PutMapping
    public ResponseEntity<Void> editCharacter(@RequestBody CharacterDTO saveCharacterDTO){
        return service.editCharacter(saveCharacterDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteCharacter(@RequestParam Long id){
        return service.softDeleteCharacter(id);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteCharacter(@RequestParam Long id){
        return service.hardDeleteCharacter(id);
    }

    @GetMapping(path = "{characterId}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable("characterId") Long id){
        return service.getCharacterById(id);
    }
}
