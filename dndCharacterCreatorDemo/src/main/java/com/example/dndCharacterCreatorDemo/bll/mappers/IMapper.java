package com.example.dndCharacterCreatorDemo.bll.mappers;

import java.util.List;

public interface IMapper<Dto, Entity> {
    public Entity fromDto(Dto dto);
    public Dto toDto(Entity entity);
    public List<Entity> fromDtos(List<Dto> dtos);
    public List<Dto> toDtos(List<Entity> entities);
}
