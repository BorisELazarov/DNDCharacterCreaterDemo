package com.example.dndcharactercreatordemo.bll.dtos;

import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.List;

public record ClassDTO(Long id, Boolean isDeleted,
                       String name, String description,
                       HitDiceEnum hitDice,
                       List<ProficiencyDTO> proficiencies){
    public ClassDTO{
        if (isDeleted==null)
        {
            isDeleted=false;
        }
    }
}
