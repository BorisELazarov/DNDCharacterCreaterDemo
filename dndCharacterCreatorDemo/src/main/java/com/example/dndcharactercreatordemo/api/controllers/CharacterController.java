package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/characters")
public class CharacterController {
    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping(path="/{userId}")
    public ResponseEntity<List<CharacterDTO>> getCharacters(
            @PathVariable Long userId,
            @RequestParam Optional<String> name,
            @RequestParam Optional<Byte> level,
            @RequestParam Optional<String> className,
            @RequestParam Optional<String> sortBy,
            @RequestParam(defaultValue = "true") boolean ascending){
        return new ResponseEntity<>(
                service.getCharacters(userId,false, name,
                        level, className, sortBy, ascending),
            HttpStatus.OK
        );
    }

    @GetMapping(path = "/deleted")
    public ResponseEntity<List<CharacterDTO>> getDeletedCharacters(
            @PathVariable Long userId,
            @RequestParam Optional<String> name,
            @RequestParam Optional<Byte> level,
            @RequestParam Optional<String> className,
            @RequestParam Optional<String> sortBy,
            @RequestParam(defaultValue = "true") boolean ascending){
        return new ResponseEntity<>(
                service.getCharacters(userId,true, name,
                        level, className, sortBy, ascending),
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
