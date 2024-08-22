package com.example.dndcharactercreatordemo.bll.dtos.characters;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import jakarta.validation.constraints.NotNull;

public record ProficiencyCharacterDTO(
        @NotNull
        ProficiencyDTO proficiency,
        Boolean expertise) {
}
