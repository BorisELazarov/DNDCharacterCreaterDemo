package com.example.dndcharactercreatordemo.bll.dtos;


import java.beans.ConstructorProperties;

public class ProficiencyDTO extends BaseDTO{

    private final String name;
    private final String type;
    public ProficiencyDTO(Long id, Boolean isDeleted,
                          String name, String type) {
        super(id, isDeleted);
        this.name = name;
        this.type = type;
    }
    public ProficiencyDTO(String name, String type){
        super();
        this.name=name;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
