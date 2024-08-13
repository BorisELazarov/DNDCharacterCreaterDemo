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
    private final SpellRepo repo;
    private final IMapper<SpellDTO, Spell> mapper;

    public SpellServiceImpl(SpellRepo repo, IMapper<SpellDTO, Spell> mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }


    private void spellNotFound(){
        throw new IllegalArgumentException("Spell not found!");
    }

    @Override
    public List<SpellDTO> getSpells() {
        return mapper.toDTOs(repo.findAll());
    }

    @Override
    public SpellDTO getSpell(Long id){
        Optional<Spell> spell=repo.findById(id);
        if (spell.isEmpty())
            spellNotFound();
        return mapper.toDto(spell.get());
    }

    @Override
    public void addSpell(SpellDTO spellDTO){
        Spell spell=repo.findByName(spellDTO.name());
        if (spell!=null && !spell.getIsDeleted()){
            throw new IllegalArgumentException("Error: there is already spell with such name!");
        }
        repo.save(mapper.fromDto(spellDTO));
    }

    @Override
    public void editSpell(SpellDTO spellDTO, Long id) {
        if (repo.existsById(id))
            repo.save(mapper.fromDto(spellDTO));
        else
            spellNotFound();
    }

    @Override
    public void softDeleteSpell(Long id){
        Optional<Spell> optionalSpell=repo.findById(id);
        if (optionalSpell.isPresent()){
            Spell spell=optionalSpell.get();
            spell.setIsDeleted(true);
            repo.save(spell);

        }else {
            spellNotFound();
        }
    }
}
