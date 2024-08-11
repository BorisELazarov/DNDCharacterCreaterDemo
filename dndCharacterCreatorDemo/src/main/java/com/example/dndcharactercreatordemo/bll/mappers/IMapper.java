package com.example.dndcharactercreatordemo.bll.mappers;

import java.util.List;

public interface IMapper<S, R, K> {
    public K fromDto(S dto);
    public K fromDto(S dto, Long id);
    public R toDto(K entity);
    public List<K> fromDtos(List<S> dtos);
    public List<R> toDtos(List<K> entities);
}
