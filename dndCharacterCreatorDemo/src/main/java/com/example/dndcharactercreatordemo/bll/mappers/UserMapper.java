package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.UserDTO;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;

import java.util.List;

public class UserMapper implements IMapper<UserDTO,User>{
    private final RoleRepo roleRepo;

    public UserMapper(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public User fromDto(UserDTO dto) {
        if(dto==null)
            return null;
        User entity=new User();
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        Role role=roleRepo.findByTitle(dto.getRole());
        role.setTitle(dto.getRole());
        entity.setRole(role);
        return entity;
    }

    @Override
    public UserDTO toDto(User entity) {
        if(entity==null)
            return null;
        return new UserDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole().getTitle(),
                entity.getIsDeleted()
        );
    }

    @Override
    public List<User> fromDtos(List<UserDTO> userDTOS) {
        return userDTOS.stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<UserDTO> toDtos(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }
}
