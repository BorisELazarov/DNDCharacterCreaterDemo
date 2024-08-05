package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.SpellDTO;
import com.example.dndcharactercreatordemo.dal.entities.Spell;

import java.util.List;

public class SpellMapper implements IMapper<SpellDTO, Spell>{
    @Override
    public Spell fromDto(SpellDTO spellDTO) {
        if (spellDTO==null)
            return null;
        Spell spell=new Spell();
        spell.setId(spellDTO.id());
        spell.setName(spellDTO.name());
        spell.setDescription(spellDTO.description());
        spell.setLevel(spellDTO.level());
        spell.setCastingRange(spellDTO.castingRange());
        spell.setTarget(spellDTO.target());
        spell.setComponents(spellDTO.components());
        spell.setDuration(spellDTO.duration());
        spell.setCastingTime(spellDTO.castingTime());
        return spell;
    }

    @Override
    public SpellDTO toDto(Spell spell) {
        if(spell==null)
            return null;
        return new SpellDTO(
                spell.getId(),spell.getIsDeleted(),
                spell.getName(), spell.getLevel(),
                spell.getCastingTime(), spell.getCastingRange(),
                spell.getTarget(), spell.getComponents(),
                spell.getDuration(), spell.getDescription()
        );
    }

    @Override
    public List<Spell> fromDtos(List<SpellDTO> spellDTOS) {
        return spellDTOS.stream().map(this::fromDto).toList();
    }

    @Override
    public List<SpellDTO> toDtos(List<Spell> spells) {
        return spells.stream().map(this::toDto).toList();
    }
}
