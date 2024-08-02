package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.ClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.ProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

import java.util.List;
import java.util.stream.Collectors;

public class ClassMapper implements IMapper<ClassDTO,DNDclass>{
    private final IMapper<ProficiencyDTO, Proficiency> proficiencyMapper;
    public ClassMapper(){
        proficiencyMapper=new ProficiencyMapper();
    }
    @Override
    public DNDclass fromDto(ClassDTO classDTO) {
        if(classDTO==null)
            return null;
        DNDclass dndClass=new DNDclass();
        dndClass.setName(classDTO.getName());
        dndClass.setHitDice(classDTO.getHitDice());
        dndClass.setDescription(classDTO.getDescription());
        dndClass.setIsDeleted(classDTO.getIsDeleted());
        dndClass.setProficiencies(
                proficiencyMapper.fromDtos(classDTO.getProficiencies())
                        .stream().collect(Collectors.toSet())
        );
        return dndClass;
    }

    @Override
    public ClassDTO toDto(DNDclass dndClass) {
        if(dndClass==null)
            return null;
        return new ClassDTO(dndClass.getId(),dndClass.getIsDeleted(),
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
    public List<DNDclass> fromDtos(List<ClassDTO> classDTOS) {
        return classDTOS.stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<ClassDTO> toDtos(List<DNDclass> dndClasses) {
        return dndClasses.stream()
                .map(this::toDto)
                .toList();
    }
}
