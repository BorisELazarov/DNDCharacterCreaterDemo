package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpellServiceImpl implements SpellService {
    private final SpellRepo spellRepo;
    private final IMapper<SpellDTO, Spell> mapper;

    public SpellServiceImpl(SpellRepo spellRepo, IMapper<SpellDTO, Spell> mapper) {
        this.spellRepo = spellRepo;
        this.mapper = mapper;
    }


    private void spellNotFound(){
        throw new IllegalArgumentException("Spell not found!");
    }

    @Override
    public List<SpellDTO> getSpells() {
        return mapper.toDTOs(spellRepo.findAll());
    }

    @Override
    public SpellDTO getSpell(Long id){
        Optional<Spell> spell= spellRepo.findById(id);
        if (spell.isEmpty())
            spellNotFound();
        return mapper.toDto(spell.get());
    }

    @Override
    public void addSpell(SpellDTO spellDTO){
        Optional<Spell> spell= spellRepo.findByName(spellDTO.name());
        if (spell.isPresent()){
            throw new IllegalArgumentException("Error: there is already spell with such name!");
        }
        spellRepo.save(mapper.fromDto(spellDTO));
    }

    @Override
    public void editSpell(SpellDTO spellDTO, Long id) {
        if (spellRepo.existsById(id))
            spellRepo.save(mapper.fromDto(spellDTO));
        else
            spellNotFound();
    }

    @Override
    public void softDeleteSpell(Long id){
        Optional<Spell> optionalSpell= spellRepo.findById(id);
        if (optionalSpell.isPresent()){
            Spell spell=optionalSpell.get();
            spell.setIsDeleted(true);
            spellRepo.save(spell);

        }else {
            spellNotFound();
        }
    }

    @Override
    public void hardDeleteSpell(Long id){
        Optional<Spell> optionalSpell= spellRepo.findById(id);
        if (optionalSpell.isPresent()){
            Spell spell=optionalSpell.get();
            if (spell.getIsDeleted()){
                spellRepo.delete(spell);
            }
        }else {
            spellNotFound();
        }
    }
}
