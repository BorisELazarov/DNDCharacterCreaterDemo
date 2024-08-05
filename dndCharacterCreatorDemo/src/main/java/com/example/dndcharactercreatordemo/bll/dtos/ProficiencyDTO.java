package com.example.dndcharactercreatordemo.bll.dtos;

public record ProficiencyDTO(Long id, Boolean isDeleted,
                             String name, String type){
    public ProficiencyDTO{
        if (isDeleted==null)
        {
            isDeleted=false;
        }
    }
}
