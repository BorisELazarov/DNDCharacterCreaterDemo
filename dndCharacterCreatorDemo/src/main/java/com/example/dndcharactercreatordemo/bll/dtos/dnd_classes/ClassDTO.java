package com.example.dndcharactercreatordemo.bll.dtos.dnd_classes;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Optional;

public record ClassDTO(Optional<Long> id, Boolean isDeleted,
                       @Size(min = 3, max=40)
                       @NotNull(message = "Name must not be empty")
                       String name,
                       @Size(min = 3, max=65535)
                       @NotNull(message = "Description must not be empty")
                       String description,
                       @NotNull(message = "The hit dice must not be empty")
                       HitDiceEnum hitDice,
                       @NotEmpty
                       @NotNull
                       List<ProficiencyDTO> proficiencies){
    public ClassDTO {
        proficiencies=List.copyOf(proficiencies);
    }
}
