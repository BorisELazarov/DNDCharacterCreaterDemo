package com.example.dndcharactercreatordemo.bll.mappers.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dndcharactercreatordemo.dal.entities.Spell;

public interface SpellMapper extends ISingleParameterMapper<SpellDTO, Spell> {
}
