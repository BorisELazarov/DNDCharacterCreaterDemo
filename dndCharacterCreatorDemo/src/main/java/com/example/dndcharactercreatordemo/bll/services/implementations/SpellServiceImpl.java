package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.SpellMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpellServiceImpl implements SpellService {
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
                HttpStatus.BAD_REQUEST
        );
    }

    @Override
    public ResponseEntity<SpellDTO> getSpell(Long id){
        Optional<Spell> spell= spellRepo.findById(id);
        if (spell.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(
                mapper.toDto(spell.get()),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> addSpell(SpellDTO spellDTO){
        Optional<Spell> spell= spellRepo.findByName(spellDTO.name());
        if (spell.isPresent()){
            throw new IllegalArgumentException("Error: there is already spell with such name!");
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
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
