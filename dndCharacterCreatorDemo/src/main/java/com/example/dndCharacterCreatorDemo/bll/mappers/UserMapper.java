package com.example.dndCharacterCreatorDemo.bll.mappers;

import com.example.dndCharacterCreatorDemo.bll.dtos.UserDTO;
import com.example.dndCharacterCreatorDemo.dal.entities.Role;
import com.example.dndCharacterCreatorDemo.dal.entities.User;
import com.example.dndCharacterCreatorDemo.dal.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserMapper implements IMapper<UserDTO,User>{

    @Override
    public User fromDto(UserDTO dto) {
        User entity=new User();
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        Role role=new Role();
        role.setTitle(dto.getRole());
        entity.setRole(role);
        return entity;
    }

    @Override
    public UserDTO toDto(User entity) {
        UserDTO dto=new UserDTO(entity.getId());
        dto.setIsDeleted(entity.getIsDeleted());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole().getTitle());
        return dto;
    }
}
