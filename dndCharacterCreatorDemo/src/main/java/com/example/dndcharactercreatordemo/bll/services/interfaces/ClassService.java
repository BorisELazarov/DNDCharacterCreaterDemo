package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.SearchClassDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ClassService {
    List<ClassDTO> getClasses(boolean isDeleted,
                              SearchClassDTO searchClassDTO);

    ClassDTO addClass(ClassDTO classDTO);

    @Transactional
    ClassDTO updateClass(ClassDTO classDTO);

    void softDeleteClass(Long id);

    void hardDeleteClass(Long id);

    ClassDTO getClassById(Long id);

    void restoreClass(Long id);

    List<ClassDTO> getClassesUnfiltered();
}
