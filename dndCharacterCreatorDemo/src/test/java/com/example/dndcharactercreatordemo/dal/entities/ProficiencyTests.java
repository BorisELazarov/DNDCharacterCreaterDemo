package com.example.dndcharactercreatordemo.dal.entities;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProficiencyTests {
    @Test
    void equalsOthers(){
        Proficiency proficiency1=new Proficiency();
        proficiency1.setName("Archery");
        proficiency1.setType("Weapons");
        proficiency1.setIsDeleted(true);
        Proficiency proficiency2=new Proficiency();
        proficiency2.setName("Archery");
        proficiency2.setType("Weapons");
        proficiency2.setIsDeleted(true);
        assertEquals(proficiency1,proficiency2);
    }
    @Test
    void equalsIds(){

        Proficiency proficiency1=new Proficiency(1L);
        proficiency1.setName("Archery");
        proficiency1.setType("Weapons");
        proficiency1.setIsDeleted(true);
        Proficiency proficiency2=new Proficiency(1L);
        proficiency2.setName("Archery");
        proficiency2.setType("Skills");
        proficiency2.setIsDeleted(true);
        assertEquals(proficiency1,proficiency2);
    }
    @Test
    void hash(){
        Proficiency proficiency1=new Proficiency(1L);
        proficiency1.setName("Archery");
        proficiency1.setType("Weapons");
        proficiency1.setIsDeleted(true);
        assertEquals(proficiency1.hashCode(),
                Objects.hash(proficiency1.getName(),proficiency1.getType()));
    }
}
