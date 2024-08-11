package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.users.ReadUserDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.SaveUserDTO;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;

import java.util.List;

public class UserMapper implements IMapper<SaveUserDTO,ReadUserDTO,User>{
    private final RoleRepo roleRepo;

    public UserMapper(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public User fromDto(SaveUserDTO dto) {
        if(dto==null)
            return null;
        User entity=new User();
        entity.setIsDeleted(dto.isDeleted());
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        Role role=roleRepo.findByTitle(dto.role());
        role.setTitle(dto.role());
        entity.setRole(role);
        return entity;
    }

    @Override
    public User fromDto(SaveUserDTO dto, Long id) {
        if(dto==null)
            return null;
        User entity=new User();
        entity.setId(id);
        entity.setIsDeleted(dto.isDeleted());
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        Role role=roleRepo.findByTitle(dto.role());
        role.setTitle(dto.role());
        entity.setRole(role);
        return entity;
    }

    @Override
    public ReadUserDTO toDto(User entity) {
        if(entity==null)
            return null;
        return new ReadUserDTO(
                entity.getId(),
                entity.getIsDeleted(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole().getTitle()
        );
    }

    @Override
    public List<User> fromDtos(List<SaveUserDTO> userDTOS) {
        return userDTOS.stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<ReadUserDTO> toDtos(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }
}
