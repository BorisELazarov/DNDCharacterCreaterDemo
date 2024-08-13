package com.example.dndcharactercreatordemo.bll.dtos.dnd_classes;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.List;
import java.util.Optional;

public record ClassDTO(Optional<Long> id, Boolean isDeleted,
                       String name, String description,
                       HitDiceEnum hitDice,
                       List<ProficiencyDTO> proficiencies){
    public ClassDTO {
        proficiencies=List.copyOf(proficiencies);
    }
}
