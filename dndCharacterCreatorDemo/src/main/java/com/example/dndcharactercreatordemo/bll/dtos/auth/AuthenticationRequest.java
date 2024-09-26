package com.example.dndcharactercreatordemo.bll.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank String email, @NotBlank String password){
}
