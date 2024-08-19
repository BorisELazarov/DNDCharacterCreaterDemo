package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClassService {
    ResponseEntity<List<ClassDTO>> getClasses();

    ResponseEntity<Void> addClass(ClassDTO classDTO);

    @Transactional
    ResponseEntity<Void> updateClass(Long id, String username, String description, HitDiceEnum hitDice);

    ResponseEntity<Void> softDeleteClass(Long id);

    ResponseEntity<Void> hardDeleteClass(Long id);

    ResponseEntity<ClassDTO> getClass(Long id);
}
