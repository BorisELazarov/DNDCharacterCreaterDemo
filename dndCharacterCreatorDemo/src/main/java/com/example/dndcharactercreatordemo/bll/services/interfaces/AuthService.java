package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.auth.AuthenticationRequest;
import com.example.dndcharactercreatordemo.bll.dtos.auth.AuthenticationResponse;
import com.example.dndcharactercreatordemo.bll.dtos.auth.RegisterRequest;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
