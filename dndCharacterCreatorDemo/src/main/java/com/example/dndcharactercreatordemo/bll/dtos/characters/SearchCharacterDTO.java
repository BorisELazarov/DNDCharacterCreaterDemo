package com.example.dndcharactercreatordemo.bll.dtos.characters;

import com.example.dndcharactercreatordemo.common.Sort;
import com.example.dndcharactercreatordemo.filters.CharacterFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchCharacterDTO(CharacterFilter filter, Sort sort) {
}
