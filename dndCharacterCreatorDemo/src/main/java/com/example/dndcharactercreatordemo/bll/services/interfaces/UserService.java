package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getUsers(Optional<String> username,
                           Optional<String> email,
                           Optional<String> roleTitle,
                           Optional<String> sortBy,
                           boolean ascending);

    UserDTO addUser(UserDTO userDTO);

    void changeUsername(Long id, String username);

    void changePassword(Long id, String oldPassword, String newPassword);

    void changeEmail(Long id, String email);

    void changeRole(Long id, String role);

    void softDeleteUser(Long id);

    void hardDeleteUser(Long id);

    void restoreUser(String username, String password);

    UserDTO getUser(Long id);

    UserDTO getUser(String email, String password);
}
