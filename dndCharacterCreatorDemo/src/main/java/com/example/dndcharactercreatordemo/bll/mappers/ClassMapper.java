package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.ClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.ProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

import java.util.LinkedHashSet;
import java.util.List;

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
