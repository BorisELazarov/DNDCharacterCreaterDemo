package com.example.dndcharactercreatordemo.filters;

import java.util.Optional;

/**
 * @author boriselazarov@gmail
 */
public record CharacterFilter(String name, Optional<Byte> level, String dndClassName) {
}
