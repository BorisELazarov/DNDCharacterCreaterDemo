package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<List<UserDTO>> getUsers();

    ResponseEntity<Void> addUser(UserDTO userDTO);

    @Transactional
    ResponseEntity<Void> updateUser(Long id, String username, String password);

    ResponseEntity<Void> softDeleteUser(Long id);

    ResponseEntity<Void> hardDeleteUser(Long id);

    ResponseEntity<UserDTO> getUser(Long id);
}
