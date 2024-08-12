package com.example.dndcharactercreatordemo.bll.dtos.users;

import java.util.Optional;

public record ReadUserDTO(Long id, Boolean isDeleted,
                          String username, String password,
                          String role) {
}
