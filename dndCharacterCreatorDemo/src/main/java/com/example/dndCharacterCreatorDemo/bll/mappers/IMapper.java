package com.example.dndCharacterCreatorDemo.bll.mappers;

public interface IMapper<Dto, Entity> {
    public Entity fromDto(Dto dto);
    public Dto toDto(Entity entity);
}
