package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.auth.AuthenticationRequest;
import com.example.dndcharactercreatordemo.bll.dtos.auth.AuthenticationResponse;
import com.example.dndcharactercreatordemo.bll.dtos.users.RegisterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;

public interface AuthService {

    AuthenticationResponse register(RegisterDTO registerDTO);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
