package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    void addUser(UserDTO userDTO);

    @Transactional
    void changePassword(Long id, String oldPassword, String newPassword);

    void softDeleteUser(Long id);

    void hardDeleteUser(Long id);

    void restoreUser(String username, String password);

    UserDTO getUser(Long id);

    UserDTO getUser(String username, String password);
    @Transactional
    void changeUsername(Long id, String username);
}
