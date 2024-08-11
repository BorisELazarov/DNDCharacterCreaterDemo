package com.example.dndcharactercreatordemo.bll.dtos.proficiencies;

public record ReadProficiencyDTO(Long id, Boolean isDeleted,
                                 String name, String type){
    public ReadProficiencyDTO {
        if (isDeleted==null)
        {
            isDeleted=false;
        }
    }
}
