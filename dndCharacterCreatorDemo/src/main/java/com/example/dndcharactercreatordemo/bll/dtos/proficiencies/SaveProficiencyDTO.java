package com.example.dndcharactercreatordemo.bll.dtos.proficiencies;

public record SaveProficiencyDTO(Boolean isDeleted,
                                 String name, String type) {
    public SaveProficiencyDTO{
        if (isDeleted==null)
        {
            isDeleted=false;
        }
    }
}
