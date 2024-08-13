package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper implements IMapper<UserDTO, User>{
    private final RoleRepo roleRepo;

    public UserMapper(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public User fromDto(UserDTO dto) {
        if(dto==null)
            return null;
        User entity=new User();
        if (dto.id().isPresent())
            entity.setId(dto.id().get());
        entity.setIsDeleted(dto.isDeleted());
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        Role role=roleRepo.findByTitle(dto.role());
        role.setTitle(dto.role());
        entity.setRole(role);
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
