package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(
                service.getAll(),
            HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Void> addCharacter(@RequestBody @Valid CharacterDTO createCharacterDTO){
        service.addCharacter(createCharacterDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> editCharacter(@RequestBody @Valid CharacterDTO saveCharacterDTO){
        service.editCharacter(saveCharacterDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteCharacter(@RequestParam Long id){
        service.softDeleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteCharacter(@RequestParam Long id){
        service.hardDeleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{characterId}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable("characterId") Long id){
        return new ResponseEntity<>(
                service.getCharacterById(id),
                HttpStatus.OK
        );
    }
}
