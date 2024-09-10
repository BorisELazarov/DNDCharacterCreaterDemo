package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClassService {
    List<ClassDTO> getClasses(boolean isDeleted,
                              Optional<String> name,
                              Optional<HitDiceEnum> hitDice,
                              Optional<String> sortBy,
                              boolean ascending);

    ClassDTO addClass(ClassDTO classDTO);

    @Transactional
    ClassDTO updateClass(ClassDTO classDTO);

    void softDeleteClass(Long id);

    void hardDeleteClass(Long id);

    ClassDTO getClassById(Long id);

    void restoreClass(Long id);
}
