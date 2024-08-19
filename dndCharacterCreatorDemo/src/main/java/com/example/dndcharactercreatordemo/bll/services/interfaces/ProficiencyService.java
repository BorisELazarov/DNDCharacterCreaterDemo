package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProficiencyService {
    ResponseEntity<List<ProficiencyDTO>> getProficiencies();
    ResponseEntity<Void> addProficiency(ProficiencyDTO proficiencyDTO);
    ResponseEntity<ProficiencyDTO> getProficiency(Long id);
    ResponseEntity<Void> updateProficiency(Long id, String name, String type);
    ResponseEntity<Void> softDeleteProficiency(Long id);

    ResponseEntity<Void> hardDeleteProficiency(Long id);
}
