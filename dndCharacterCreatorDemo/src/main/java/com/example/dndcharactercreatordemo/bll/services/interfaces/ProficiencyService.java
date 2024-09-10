package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;

import java.util.List;
import java.util.Optional;

public interface ProficiencyService {
    List<ProficiencyDTO> getProficiencies(boolean isDeleted, Optional<String> name,
                                          Optional<String> type, Optional<String> sortBy,
                                          boolean ascending);
    ProficiencyDTO addProficiency(ProficiencyDTO proficiencyDTO);
    ProficiencyDTO getProficiency(Long id);
    ProficiencyDTO updateProficiency(ProficiencyDTO proficiencyDTO);
    void softDeleteProficiency(Long id);
    void hardDeleteProficiency(Long id);
    void restoreProficiency(Long id);
}
