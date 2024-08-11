package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.spells.ReadSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SaveSpellDTO;
import com.example.dndcharactercreatordemo.dal.entities.Spell;

import java.util.List;

public class SpellMapper implements IMapper<SaveSpellDTO, ReadSpellDTO, Spell>{
    @Override
    public Spell fromDto(SaveSpellDTO spellDTO) {
        if (spellDTO==null)
            return null;
        Spell spell=new Spell();
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
    public Spell fromDto(SaveSpellDTO spellDTO, Long id) {
        if (spellDTO==null)
            return null;
        Spell spell=new Spell();
        spell.setId(id);
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
    public ReadSpellDTO toDto(Spell spell) {
        if(spell==null)
            return null;
        return new ReadSpellDTO(
                spell.getId(),spell.getIsDeleted(),
                spell.getName(), spell.getLevel(),
                spell.getCastingTime(), spell.getCastingRange(),
                spell.getTarget(), spell.getComponents(),
                spell.getDuration(), spell.getDescription()
        );
    }

    @Override
    public List<Spell> fromDtos(List<SaveSpellDTO> spellDTOS) {
        return spellDTOS.stream().map(this::fromDto).toList();
    }

    @Override
    public List<ReadSpellDTO> toDtos(List<Spell> spells) {
        return spells.stream().map(this::toDto).toList();
    }
}
