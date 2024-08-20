package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;

import java.util.List;

public interface ProficiencyService {
    List<ProficiencyDTO> getProficiencies();
    void addProficiency(ProficiencyDTO proficiencyDTO);
    ProficiencyDTO getProficiency(Long id);
    void updateProficiency(Long id, String name, String type);
    void softDeleteProficiency(Long id);
    void hardDeleteProficiency(Long id);
}
