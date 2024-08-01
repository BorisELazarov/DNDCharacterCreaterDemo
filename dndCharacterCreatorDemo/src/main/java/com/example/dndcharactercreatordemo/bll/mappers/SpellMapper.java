package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.SpellDTO;
import com.example.dndcharactercreatordemo.dal.entities.Spell;

import java.util.List;
import java.util.stream.Collectors;

public class SpellMapper implements IMapper<SpellDTO, Spell>{
    @Override
    public Spell fromDto(SpellDTO spellDTO) {
        if (spellDTO==null)
            return null;
        Spell spell=new Spell(spellDTO.getId());
        spell.setName(spellDTO.getName());
        spell.setDescription(spellDTO.getDescription());
        spell.setLevel(spellDTO.getLevel());
        spell.setCastingRange(spellDTO.getCastingRange());
        spell.setTarget(spellDTO.getTarget());
        spell.setComponents(spellDTO.getComponents());
        spell.setDuration(spellDTO.getDuration());
        spell.setCastingTime(spellDTO.getCastingTime());
        return spell;
    }

    @Override
    public SpellDTO toDto(Spell spell) {
        if(spell==null)
            return null;
        SpellDTO spellDTO=new SpellDTO();
        spellDTO.setId(spell.getId());
        spellDTO.setName(spell.getName());
        spellDTO.setDescription(spell.getDescription());
        spellDTO.setLevel(spell.getLevel());
        spellDTO.setCastingRange(spell.getCastingRange());
        spellDTO.setTarget(spell.getTarget());
        spellDTO.setComponents(spell.getComponents());
        spellDTO.setDuration(spell.getDuration());
        spellDTO.setCastingTime(spell.getCastingTime());
        return spellDTO;
    }

    @Override
    public List<Spell> fromDtos(List<SpellDTO> spellDTOS) {
        return spellDTOS.stream()
                .map(x-> fromDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<SpellDTO> toDtos(List<Spell> spells) {
        return spells.stream()
                .map(x-> toDto(x))
                .collect(Collectors.toList());
    }
}
