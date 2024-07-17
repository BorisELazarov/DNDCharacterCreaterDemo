package com.example.dndCharacterCreatorDemo.bll.mappers;

import com.example.dndCharacterCreatorDemo.bll.dtos.ClassDTO;
import com.example.dndCharacterCreatorDemo.dal.entities.DNDclass;

public class ClassMapper implements IMapper<ClassDTO,DNDclass>{
    @Override
    public DNDclass fromDto(ClassDTO classDTO) {
        DNDclass dndClass=new DNDclass();
        dndClass.setName(classDTO.getName());
        dndClass.setHitDice(classDTO.getHitDice());
        dndClass.setDescription(classDTO.getDescription());
        dndClass.setIsDeleted(classDTO.getIsDeleted());
        return dndClass;
    }

    @Override
    public ClassDTO toDto(DNDclass dndClass) {
        ClassDTO classDTO = new ClassDTO(dndClass.getId());
        classDTO.setDescription(dndClass.getDescription());
        classDTO.setName(dndClass.getName());
        classDTO.setHitDice(dndClass.getHitDice());
        classDTO.setIsDeleted(dndClass.getIsDeleted());
        return  classDTO;
    }
}
