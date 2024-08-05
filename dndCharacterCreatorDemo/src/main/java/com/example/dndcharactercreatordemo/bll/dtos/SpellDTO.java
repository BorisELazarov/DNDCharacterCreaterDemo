package com.example.dndcharactercreatordemo.bll.dtos;

public record SpellDTO(Long id, Boolean isDeleted,
                       String name, int level,
                       String castingTime, int castingRange,
                       String target, String components,
                       int duration, String description){
}
