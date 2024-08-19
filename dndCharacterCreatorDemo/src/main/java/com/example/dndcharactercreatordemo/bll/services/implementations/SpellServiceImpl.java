package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.SpellMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpellServiceImpl implements SpellService {
    private static final String NOT_FOUND_MESSAGE="The spell is not found!";
    private static final String NAME_TAKEN_MESSAGE="There is already spell with that name!";
    private final SpellRepo spellRepo;
    private final SpellMapper mapper;

    public SpellServiceImpl(SpellRepo spellRepo, SpellMapper mapper) {
        this.spellRepo = spellRepo;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<SpellDTO>> getSpells() {
        return new ResponseEntity<>(
                mapper.toDTOs(spellRepo.findAll()),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<SpellDTO> getSpell(Long id){
        Optional<Spell> spell= spellRepo.findById(id);
        if (spell.isEmpty())
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        return new ResponseEntity<>(
                mapper.toDto(spell.get()),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> addSpell(SpellDTO spellDTO){
        Optional<Spell> spell= spellRepo.findByName(spellDTO.name());
        if (spell.isPresent()){
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }
        spellRepo.save(mapper.fromDto(spellDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> editSpell(SpellDTO spellDTO, Long id) {
        if (spellRepo.existsById(id)) {
            spellRepo.save(mapper.fromDto(spellDTO));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }

    @Override
    public ResponseEntity<Void> softDeleteSpell(Long id){
        Optional<Spell> optionalSpell= spellRepo.findById(id);
        if (optionalSpell.isPresent()){
            Spell spell=optionalSpell.get();
            spell.setIsDeleted(true);
            spellRepo.save(spell);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public ResponseEntity<Void> hardDeleteSpell(Long id){
        Optional<Spell> optionalSpell= spellRepo.findById(id);
        if (optionalSpell.isPresent()){
            Spell spell=optionalSpell.get();
            if (spell.getIsDeleted()){
                spellRepo.delete(spell);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            throw new NotSoftDeletedException("The spell must be soft deleted first!");
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
}
