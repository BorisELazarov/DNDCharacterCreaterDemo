package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.dndClasses.ReadClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.dndClasses.SaveClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ReadProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.SaveProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

import java.util.LinkedHashSet;
import java.util.List;

public class ClassMapper implements IMapper<SaveClassDTO, ReadClassDTO, DNDclass>{
    private final IMapper<SaveProficiencyDTO, ReadProficiencyDTO, Proficiency> proficiencyMapper;
    public ClassMapper(){
        proficiencyMapper=new ProficiencyMapper();
    }
    @Override
    public DNDclass fromDto(SaveClassDTO classDTO) {
        if(classDTO==null)
            return null;
        DNDclass dndClass=new DNDclass();
        dndClass.setName(classDTO.name());
        dndClass.setHitDice(classDTO.hitDice());
        dndClass.setDescription(classDTO.description());
        dndClass.setIsDeleted(classDTO.isDeleted());
        dndClass.setProficiencies(
                new LinkedHashSet<>(proficiencyMapper.fromDtos(classDTO.proficiencies()))
        );
        return dndClass;
    }

    @Override
    public DNDclass fromDto(SaveClassDTO classDTO, Long id) {
        if(classDTO==null)
            return null;
        DNDclass dndClass=new DNDclass();
        dndClass.setId(id);
        dndClass.setName(classDTO.name());
        dndClass.setHitDice(classDTO.hitDice());
        dndClass.setDescription(classDTO.description());
        dndClass.setIsDeleted(classDTO.isDeleted());
        dndClass.setProficiencies(
                new LinkedHashSet<>(proficiencyMapper.fromDtos(classDTO.proficiencies()))
        );
        return dndClass;
    }

    @Override
    public ReadClassDTO toDto(DNDclass dndClass) {
        if(dndClass==null)
            return null;
        return new ReadClassDTO(dndClass.getId(),dndClass.getIsDeleted(),
                dndClass.getName(),
                dndClass.getDescription(),
                dndClass.getHitDice(),
                proficiencyMapper.toDtos(
                        dndClass.getProficiencies()
                                .stream().toList()
                )
        );
    }

    @Override
    public List<DNDclass> fromDtos(List<SaveClassDTO> classDTOS) {
        return classDTOS.stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<ReadClassDTO> toDtos(List<DNDclass> dndClasses) {
        return dndClasses.stream()
                .map(this::toDto)
                .toList();
    }
}
