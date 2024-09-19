package com.example.dndcharactercreatordemo.bll.dtos.proficiencies;

import com.example.dndcharactercreatordemo.common.Sort;
import com.example.dndcharactercreatordemo.filters.ProficiencyFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchProficiencyDTO(ProficiencyFilter filter, Sort sort) {
}
