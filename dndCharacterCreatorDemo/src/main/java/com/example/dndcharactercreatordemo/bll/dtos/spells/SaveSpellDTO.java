package com.example.dndcharactercreatordemo.bll.dtos.spells;

public record SaveSpellDTO(Boolean isDeleted,
                           String name, int level,
                           String castingTime, int castingRange,
                           String target, String components,
                           int duration, String description){
}
