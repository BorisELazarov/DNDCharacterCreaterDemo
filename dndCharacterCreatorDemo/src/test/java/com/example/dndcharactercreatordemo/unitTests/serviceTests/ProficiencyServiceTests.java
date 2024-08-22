package com.example.dndcharactercreatordemo.unitTests.serviceTests;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dndcharactercreatordemo.bll.services.implementations.ProficiencyServiceImpl;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProficiencyServiceTests {
    @Mock
    private ProficiencyMapper mapper;
    @Mock
    private ProficiencyRepo repo;

    @InjectMocks
    private ProficiencyServiceImpl service;

    private Proficiency proficiency;
    private ProficiencyDTO proficiencyDTO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        proficiency = new Proficiency();
        proficiency.setIsDeleted(false);
        proficiency.setType("Armor");
        proficiency.setName("Heavy");
        proficiency.setId(1L);
        proficiencyDTO=new ProficiencyDTO(
                Optional.of(1L),
                false,
                "Heavy",
                "Armor"
        );
    }

    @Test
    void getProficiencyNotEmpty(){
        Mockito.when(repo.findById(1L)).thenReturn(
                Optional.of(proficiency)
        );
        Mockito.when(mapper.toDto(proficiency)).thenReturn(proficiencyDTO);
        Mockito.when(service.getProficiency(1L)).thenReturn(proficiencyDTO);
        ProficiencyDTO dto=service.getProficiency(1L);
        assertNotNull(dto);
        //Mockito.verify(repo).findById(1L);
        Mockito.verify(mapper).toDto(proficiency);
        //Mockito.verify(service).getProficiency(1L);
    }
}
