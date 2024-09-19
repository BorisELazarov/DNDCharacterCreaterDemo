package com.example.dndcharactercreatordemo.filters;

import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.Optional;

/**
 * @author boriselazarov@gmail
 */
public record DndClassFilter(String name, Optional<HitDiceEnum> hitDice) {
}
