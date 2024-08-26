package com.example.dndcharactercreatordemo.bll.services.interfaces;

import com.example.dndcharactercreatordemo.api.AuthenticationRequest;
import com.example.dndcharactercreatordemo.api.AuthenticationResponse;
import com.example.dndcharactercreatordemo.api.RegisterRequest;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
