package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.dal.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper implements IMapper<UserDTO, User>{

    @Override
    public User fromDto(UserDTO dto) {
        if(dto==null)
            return null;
        User entity=new User();
        dto.id().ifPresent(entity::setId);
        entity.setIsDeleted(dto.isDeleted());
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        return entity;
    }

    @Override
    public UserDTO toDto(User entity) {
        if(entity==null)
            return null;
        return new UserDTO(
                entity.getId().describeConstable(),
                entity.getIsDeleted(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole().getTitle()
        );
    }

    @Override
    public List<User> fromDTOs(List<UserDTO> userDTOS) {
        return userDTOS.stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<UserDTO> toDTOs(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }
}
