package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.SpellMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @PostConstruct
    private void seedSpells(){
        if(spellRepo.count()>0)
            return;
        Set<Spell> spells=new LinkedHashSet<>();
        spells.add(
                getSpell(
                        false, "Fire",
                        1, "A",
                        20, "asfd",
                        "V, M", 0,
                        "FIreee"
                )
        );
        spells.add(
                getSpell(
                        true, "Cold",
                        1, "A",
                        25, "ads",
                        "V, M", 0,
                        "Cold cold"
                )
        );
        spells.add(
                getSpell(
                        false, "Thunder",
                        1, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Thunder"
                )
        );
        spellRepo.saveAll(spells);
    }

    private Spell getSpell(Boolean isDeleted,
                          String name, int level,
                          String castingTime, int castingRange,
                          String target, String components,
                          int duration, String description){
        Spell spell=new Spell();
        spell.setIsDeleted(isDeleted);
        spell.setName(name);
        spell.setDescription(description);
        spell.setLevel(level);
        spell.setCastingRange(castingRange);
        spell.setTarget(target);
        spell.setComponents(components);
        spell.setDuration(duration);
        spell.setCastingTime(castingTime);
        return spell;
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
