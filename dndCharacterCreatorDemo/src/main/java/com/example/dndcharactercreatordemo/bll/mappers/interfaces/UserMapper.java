package com.example.dndcharactercreatordemo.bll.mappers.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.users.RegisterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    User fromDto(UserDTO userDTO, Optional<Role> role);
    User fromDto(UserDTO userDTO);
    User fromDto(RegisterDTO registerDTO);
    UserDTO toDto(User user);
    List<UserDTO> toDTOs(List<User> entities);
}
