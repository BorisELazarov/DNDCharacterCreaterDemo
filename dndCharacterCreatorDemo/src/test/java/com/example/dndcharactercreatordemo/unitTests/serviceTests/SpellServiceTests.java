package com.example.dndcharactercreatordemo.unitTests.serviceTests;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.SpellMapper;
import com.example.dndcharactercreatordemo.bll.services.implementations.SpellServiceImpl;
import com.example.dndcharactercreatordemo.dal.entities.BaseEntity;
import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpellServiceTests {
    @Mock
    private SpellMapper mapper;
    @Mock
    private SpellRepo repo;

    @InjectMocks
    private SpellServiceImpl service;

    private Spell spell;
    private SpellDTO spellDTO;
    private List<Spell> spells;
    private List<SpellDTO> spellDTOS;

    private Spell getSpell(Long id, boolean isDeleted,String name, int level,
                           String castingTime, int castingRange,
                           String target, String components,
                           int duration, String description){
        Spell item=new Spell();
        item.setId(id);
        item.setIsDeleted(isDeleted);
        item.setName(name);
        item.setDescription(description);
        item.setLevel(level);
        item.setCastingRange(castingRange);
        item.setTarget(target);
        item.setComponents(components);
        item.setDuration(duration);
        item.setCastingTime(castingTime);
        return item;
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        spell = getSpell(1L, false, "Thunder",
                1, "A",
                20, "asfd",
                "V, M", 0,
                "Thunder");
        spellDTO = new SpellDTO(Optional.of(1L), false, "Thunder",
                1, "A",
                20, "asfd",
                "V, M", 0,
                "Thunder"
        );
        spells =List.of(
                spell,
                getSpell(2L, false, "Thunder",
                        2, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Fire"),
                getSpell(3L, false, "Thunder",
                3, "A",
                20, "asfd",
                "V, M", 0,
                "Frost"),
                getSpell(3L, false, "Thunder",
                3, "A",
                20, "asfd",
                "V, M", 0,
                "Force")
        );
        spellDTOS = List.of(
                spellDTO,
                new SpellDTO(Optional.of(2L), false, "Thunder",
                        2, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Fire"),
                new SpellDTO(Optional.of(3L), false, "Thunder",
                        3, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Frost"),
                new SpellDTO(Optional.of(4L), false, "Thunder",
                        4, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Force")
        );
        Mockito.when(mapper.toDto(spell)).thenReturn(spellDTO);
        Mockito.when(mapper.fromDto(spellDTO)).thenReturn(spell);
        Mockito.when(mapper.toDTOs(spells)).thenReturn(spellDTOS);
    }

    @Test
    void getProficiencyNotNull(){
        Mockito.when(repo.findById(1L)).thenReturn(
                Optional.of(spell)
        );
        Mockito.when(service.getSpell(1L)).thenReturn(spellDTO);
        SpellDTO dto=service.getSpell(1L);
        assertNotNull(dto);
    }

    @Test
    void getDeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(true)).thenReturn(
                spells.stream().filter(BaseEntity::getIsDeleted).toList()
        );
        Mockito.when(service.getSpells(true)).thenReturn(spellDTOS);
        List<SpellDTO> dtos=service.getSpells(true);
        assertFalse(dtos.isEmpty());
    }

    @Test
    void getUndeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(false)).thenReturn(
                spells.stream().filter(x->!x.getIsDeleted()).toList()
        );
        Mockito.when(service.getSpells(false)).thenReturn(spellDTOS);
        List<SpellDTO> dtos=service.getSpells(false);
        assertFalse(dtos.isEmpty());
    }
}
