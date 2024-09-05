package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/characters")
public class CharacterController {
    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CharacterDTO>> getCharacters(){
        return new ResponseEntity<>(
                service.getCharacters(false),
            HttpStatus.OK
        );
    }

    @GetMapping(path = "/deleted")
    public ResponseEntity<List<CharacterDTO>> getDeletedCharacters(){
        return new ResponseEntity<>(
                service.getCharacters(true),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CharacterDTO> addCharacter(@RequestBody @Valid CharacterDTO characterDTO){
        return new ResponseEntity<>(
                service.addCharacter(characterDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<CharacterDTO> editCharacter(@RequestBody @Valid CharacterDTO characterDTO){
        return new ResponseEntity<>(
                service.editCharacter(characterDTO),
                HttpStatus.OK
        );
    }

    @PutMapping(path = "/restore/{characterId}")
    public ResponseEntity<Void> restoreCharacter(@PathVariable("characterId") Long id){
        service.restoreCharacter(id);
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
