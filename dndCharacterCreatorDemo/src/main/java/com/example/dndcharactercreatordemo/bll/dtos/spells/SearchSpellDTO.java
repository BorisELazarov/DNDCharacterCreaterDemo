package com.example.dndcharactercreatordemo.bll.dtos.spells;

import com.example.dndcharactercreatordemo.common.Sort;
import com.example.dndcharactercreatordemo.filters.SpellFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchSpellDTO(SpellFilter filter, Sort sort) {
}
