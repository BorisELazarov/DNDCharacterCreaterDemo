package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import jakarta.transaction.Transactional;
import java.util.List;

public interface ClassService {
    List<ClassDTO> getClasses(boolean isDeleted);

    void addClass(ClassDTO classDTO);

    @Transactional
    void updateClass(ClassDTO classDTO);

    void softDeleteClass(Long id);

    void hardDeleteClass(Long id);

    ClassDTO getClassById(Long id);

    void restoreClass(Long id);
}
