package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    void addUser(UserDTO userDTO);

    @Transactional
    void updateUser(Long id, String username, String password);

    void softDeleteUser(Long id);

    void hardDeleteUser(Long id);

    UserDTO getUser(Long id);
}
