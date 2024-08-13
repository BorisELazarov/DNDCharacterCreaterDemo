package com.example.dndcharactercreatordemo.bll.mappers;

import java.util.List;

public interface IMapper<D, E> {
    E fromDto(D dto);
    D toDto(E entity);
    List<E> fromDTOs(List<D> dtos);
    List<D> toDTOs(List<E> entities);
}
