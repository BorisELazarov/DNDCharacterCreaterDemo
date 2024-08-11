package com.example.dndcharactercreatordemo.bll.dtos.dndClasses;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ReadProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.SaveProficiencyDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.List;

public record SaveClassDTO(Boolean isDeleted,
                           String name, String description,
                           HitDiceEnum hitDice,
                           List<SaveProficiencyDTO> proficiencies){
    public SaveClassDTO {
        if (isDeleted==null)
        {
            isDeleted=false;
        }
    }
}
