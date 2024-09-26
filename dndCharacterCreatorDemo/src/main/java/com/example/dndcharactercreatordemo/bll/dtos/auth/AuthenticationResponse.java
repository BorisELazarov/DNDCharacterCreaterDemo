package com.example.dndcharactercreatordemo.bll.dtos.auth;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationResponse(@NotBlank String token, @NotNull UserDTO user) {
}
