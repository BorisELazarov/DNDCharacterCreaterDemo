package com.example.dndcharactercreatordemo.bll.mappers;

import java.util.List;

public interface IMapper<T, K> {
    public K fromDto(T dto);
    public T toDto(K entity);
    public List<K> fromDtos(List<T> dtos);
    public List<T> toDtos(List<K> entities);
}
