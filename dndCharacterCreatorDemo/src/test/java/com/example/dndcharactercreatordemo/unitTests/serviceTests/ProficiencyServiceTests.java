package com.example.dndcharactercreatordemo.unitTests.serviceTests;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dndcharactercreatordemo.bll.services.implementations.ProficiencyServiceImpl;
import com.example.dndcharactercreatordemo.dal.entities.BaseEntity;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProficiencyServiceTests {
    @Mock
    private ProficiencyMapper mapper;
    @Mock
    private ProficiencyRepo repo;

    @InjectMocks
    private ProficiencyServiceImpl service;

    private Proficiency proficiency;
    private ProficiencyDTO proficiencyDTO;
    private Proficiency createProficiency;
    private ProficiencyDTO createProficiencyDTO;
    private List<Proficiency> proficiencies;
    private List<ProficiencyDTO> proficiencyDTOS;

    private Proficiency getProficiency(Long id, String name, String type, boolean isDeleted){
        Proficiency item=new Proficiency();
        item.setId(id);
        item.setName(name);
        item.setType(type);
        item.setIsDeleted(isDeleted);
        return item;
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        proficiency = getProficiency(1L,"Heavy","Armor",false);
        proficiencyDTO=new ProficiencyDTO(
                Optional.of(1L),
                false,
                "Heavy",
                "Armor"
        );
        createProficiency = getProficiency(null,"Heavy","Armor",false);
        createProficiencyDTO=new ProficiencyDTO(
                Optional.empty(),
                false,
                "Heavy",
                "Armor"
        );
        proficiencies=List.of(
                proficiency,
                getProficiency(2L,"Light","Armor",false),
                getProficiency(3L,"Super","Armor",true),
                getProficiency(4L,"Medium","Armor",false),
                getProficiency(5L,"Range","Attack",true)
        );
        proficiencyDTOS= List.of(
                proficiencyDTO,
                new ProficiencyDTO(Optional.of(2L),false,"Light","Armor"),
                new ProficiencyDTO(Optional.of(3L),true,"Super","Armor"),
                new ProficiencyDTO(Optional.of(4L),false,"Medium","Armor"),
                new ProficiencyDTO(Optional.of(5L),true,"Range","Attack")
        );
        Mockito.when(mapper.toDto(proficiency)).thenReturn(proficiencyDTO);
        Mockito.when(mapper.fromDto(proficiencyDTO)).thenReturn(proficiency);
    }

    @Test
    void getProficiencyNotNull(){
        Mockito.when(repo.findById(1L)).thenReturn(
                Optional.of(proficiency)
        );
        ProficiencyDTO dto=service.getProficiency(1L);
        assertNotNull(dto);
    }

    @Test
    void getDeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(true)).thenReturn(
                proficiencies.stream().filter(BaseEntity::getIsDeleted).toList()
        );
        Mockito.when(
                mapper.toDTOs(proficiencies.stream().filter(BaseEntity::getIsDeleted).toList())
        ).thenReturn(
                proficiencyDTOS.stream().filter(ProficiencyDTO::isDeleted).toList()
        );
        List<ProficiencyDTO> dtos=service.getProficiencies(true, Optional.empty(),
                Optional.empty(), Optional.empty(), true);
        assertFalse(dtos.isEmpty());
    }

    @Test
    void getUndeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(false)).thenReturn(
                proficiencies.stream().filter(x->!x.getIsDeleted()).toList()
        );
        Mockito.when(
                mapper.toDTOs(proficiencies.stream().filter(x->!x.getIsDeleted()).toList())
        ).thenReturn(
                proficiencyDTOS.stream().filter(x->!x.isDeleted()).toList()
        );
        List<ProficiencyDTO> dtos=service.getProficiencies(false, Optional.empty(),
                Optional.empty(), Optional.empty(), true);
        assertFalse(dtos.isEmpty());
    }

    @Test
    void addProficiencyReturnAreEqual(){
        Mockito.when(repo.findByName(createProficiencyDTO.name())).thenReturn(Optional.empty());
        Mockito.when(repo.save(createProficiency)).thenReturn(proficiency);
        Mockito.when(mapper.fromDto(createProficiencyDTO)).thenReturn(createProficiency);
        ProficiencyDTO dto=service.addProficiency(createProficiencyDTO);
        assertEquals(dto,proficiencyDTO);
    }

    @Test
    void editProficiencyReturnAreEqual(){
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(proficiency));
        Mockito.when(repo.findByName(createProficiencyDTO.name())).thenReturn(Optional.empty());
        Mockito.when(repo.save(mapper.fromDto(proficiencyDTO))).thenReturn(proficiency);
        assertNotNull(repo.save(mapper.fromDto(proficiencyDTO)));
        assertEquals(service.updateProficiency(proficiencyDTO),proficiencyDTO);
    }

    @Test
    void softDeleteProficiencyThrowNotFoundException(){
        Mockito.doThrow(new NotFoundException("")).when(repo).findById(0L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(proficiency));
        assertThrows(NotFoundException.class,
                ()->service.softDeleteProficiency(0L));
        assertDoesNotThrow(()->service.softDeleteProficiency(1L));
    }

    @Test
    void hardDeleteProficiencyThrowNotFoundException(){
        Mockito.doThrow(new NotFoundException("")).when(repo).findById(0L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(proficiency));
        assertThrows(NotFoundException.class,
                ()->service.hardDeleteProficiency(0L));
        assertThrows(NotSoftDeletedException.class,()->service.hardDeleteProficiency(1L));
    }
}
