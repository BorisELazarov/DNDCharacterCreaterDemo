package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ClassService {
    List<ClassDTO> getClasses();

    void addClass(ClassDTO classDTO);

    @Transactional
    void updateClass(Long id, String username, String description, HitDiceEnum hitDice);

    void softDeleteClass(Long id);

    void hardDeleteClass(Long id);

    ClassDTO getClass(Long id);
}
