package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.SearchProficiencyDTO;

import java.util.List;

public interface ProficiencyService {
    List<ProficiencyDTO> getProficiencies(boolean isDeleted, SearchProficiencyDTO searchProficiencyDTO);
    ProficiencyDTO addProficiency(ProficiencyDTO proficiencyDTO);
    ProficiencyDTO getProficiency(Long id);
    ProficiencyDTO updateProficiency(ProficiencyDTO proficiencyDTO);
    void softDeleteProficiency(Long id);
    void hardDeleteProficiency(Long id);
    void restoreProficiency(Long id);
    List<ProficiencyDTO> getProficienciesUnfiltered();
}
