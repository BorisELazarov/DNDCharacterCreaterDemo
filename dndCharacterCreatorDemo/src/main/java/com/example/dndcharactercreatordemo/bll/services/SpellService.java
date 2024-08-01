package com.example.dndcharactercreatordemo.bll.services;

import com.example.dndcharactercreatordemo.bll.dtos.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.bll.mappers.SpellMapper;
import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpellService {
    private final SpellRepo repo;
    private final IMapper<SpellDTO, Spell> mapper;

    @Autowired
    public SpellService(SpellRepo repo) {
        this.repo = repo;
        this.mapper = new SpellMapper();
    }

    private void spellNotFound(){
        throw new IllegalArgumentException("Spell not found!");
    }

    public List<SpellDTO> getSpells() {
        return mapper.toDtos(repo.findAll());
    }

    public SpellDTO getSpell(Long id){
        Optional<Spell> spell=repo.findById(id);
        if (!spell.isPresent())
            spellNotFound();
        return mapper.toDto(spell.get());
    }

    public void addSpell(SpellDTO spellDTO){
        Spell spell=repo.findByName(spellDTO.getName());
        if (spell!=null && !spell.getIsDeleted()){
            throw new IllegalArgumentException("Error: there is already spell with such name!");
        }
        repo.save(mapper.fromDto(spellDTO));
    }

    public void editSpell(SpellDTO spellDTO) {
        if (repo.existsById(spellDTO.getId()))
            repo.save(mapper.fromDto(spellDTO));
        else
            spellNotFound();
    }

    public void softDeleteSpell(Long id){
        if (!repo.existsById(id))
        {
            spellNotFound();
        }
        Spell spell=repo.findById(id).get();
        spell.setIsDeleted(true);
        repo.save(spell);
    }
}
