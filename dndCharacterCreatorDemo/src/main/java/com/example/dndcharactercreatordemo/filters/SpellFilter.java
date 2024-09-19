package com.example.dndcharactercreatordemo.filters;

import java.util.Optional;
/**
 * @author boriselazarov@gmail
 */
public record SpellFilter(String name, Optional<Byte> level,
                          String castingTime, Optional<Integer> range) {
}
