package com.example.dndcharactercreatordemo.unitTests.serviceTests;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ClassMapper;
import com.example.dndcharactercreatordemo.bll.services.implementations.ClassServiceImpl;
import com.example.dndcharactercreatordemo.dal.entities.BaseEntity;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.repos.ClassRepo;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
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

public class ClassServiceTests {
    @Mock
    private ClassMapper mapper;
    @Mock
    private ClassRepo repo;

    @InjectMocks
    private ClassServiceImpl service;

    private DNDclass dnDclass;
    private ClassDTO classDTO;
    private List<DNDclass> dnDclasses;
    private List<ClassDTO> classDTOS;

    private DNDclass getDnDclass(Long id, boolean isDeleted, String name, String description, HitDiceEnum hitDiceEnum){
        DNDclass dnDclass = new DNDclass();
        dnDclass.setName(name);
        dnDclass.setHitDice(hitDiceEnum);
        dnDclass.setIsDeleted(isDeleted);
        dnDclass.setDescription(description);
        return dnDclass;
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        dnDclass = getDnDclass(1L, false, "Fighter",
                "Fights", HitDiceEnum.D10);
        classDTO = new ClassDTO(Optional.of(1L), false, "Fighter",
                "Fights", HitDiceEnum.D10, List.of());
        dnDclasses =List.of(
                dnDclass,
                getDnDclass(2L, true, "Wizard",
                        "Cast spells", HitDiceEnum.D6),
                getDnDclass(3L, true, "Rogue",
                        "Sneaks", HitDiceEnum.D8),
                getDnDclass(4L, false, "Barbarian",
                        "Tank", HitDiceEnum.D12)
        );
        classDTOS = List.of(
                classDTO,
                new ClassDTO(Optional.of(2L), true, "Wizard",
                        "Cast spells", HitDiceEnum.D6, List.of()),
                new ClassDTO(Optional.of(3L), true, "Rogue",
                        "Sneaks", HitDiceEnum.D8, List.of()),
                new ClassDTO(Optional.of(4L), false, "Barbarian",
                        "Tank", HitDiceEnum.D12, List.of())
        );
        Mockito.when(mapper.toDto(dnDclass)).thenReturn(classDTO);
        Mockito.when(mapper.fromDto(classDTO)).thenReturn(dnDclass);
        Mockito.when(mapper.toDTOs(dnDclasses)).thenReturn(classDTOS);
    }

    @Test
    void getProficiencyNotNull(){
        Mockito.when(repo.findById(1L)).thenReturn(
                Optional.of(dnDclass)
        );
        Mockito.when(service.getClassById(1L)).thenReturn(classDTO);
        ClassDTO dto=service.getClassById(1L);
        assertNotNull(dto);
    }

    @Test
    void getDeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(true)).thenReturn(
                dnDclasses.stream().filter(BaseEntity::getIsDeleted).toList()
        );
        Mockito.when(service.getClasses(true)).thenReturn(classDTOS);
        List<ClassDTO> dtos=service.getClasses(true);
        assertFalse(dtos.isEmpty());
    }

    @Test
    void getUndeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(false)).thenReturn(
                dnDclasses.stream().filter(x->!x.getIsDeleted()).toList()
        );
        Mockito.when(service.getClasses(false)).thenReturn(classDTOS);
        List<ClassDTO> dtos=service.getClasses(false);
        assertFalse(dtos.isEmpty());
    }
}
