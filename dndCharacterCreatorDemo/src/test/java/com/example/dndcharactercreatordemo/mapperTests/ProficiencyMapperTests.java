package com.example.dndcharactercreatordemo.mapperTests;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.implementations.ProficiencyMapperImpl;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProficiencyMapperTests {
    private final ProficiencyMapper mapper=new ProficiencyMapperImpl();

    @Test
    void fromDtoAreEquals(){
        ProficiencyDTO heavyArmor=new ProficiencyDTO(
                Optional.of(1L),
                true,
                "heavy",
                "armor"
                );
        Proficiency proficiency= mapper.fromDto(heavyArmor);
        heavyArmor.id().ifPresent(id-> assertEquals(id,proficiency.getId()));
        assertEquals(heavyArmor.isDeleted(),proficiency.getIsDeleted());
        assertEquals(heavyArmor.name(),proficiency.getName());
        assertEquals(heavyArmor.type(),proficiency.getType());
    }

    @Test
    void toDtoAreEquals(){
        Proficiency proficiency= new Proficiency();
        proficiency.setId(1L);
        proficiency.setIsDeleted(false);
        proficiency.setName("heavy");
        proficiency.setType("armor");
        ProficiencyDTO dto=mapper.toDto(proficiency);
        assertTrue(dto.id().isPresent());
        assertEquals(proficiency.getId(),dto.id().get());
        assertEquals(proficiency.getIsDeleted(),dto.isDeleted());
        assertEquals(proficiency.getName(),dto.name());
        assertEquals(proficiency.getType(),dto.type());
    }


}
