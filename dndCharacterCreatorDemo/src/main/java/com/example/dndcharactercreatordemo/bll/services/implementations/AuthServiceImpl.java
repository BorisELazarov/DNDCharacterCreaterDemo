package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.api.AuthenticationRequest;
import com.example.dndcharactercreatordemo.api.AuthenticationResponse;
import com.example.dndcharactercreatordemo.api.RegisterRequest;
import com.example.dndcharactercreatordemo.api.config.JwtService;
import com.example.dndcharactercreatordemo.bll.services.interfaces.AuthService;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authManager) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user=new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<Role> role=roleRepo.findByTitle("user");
        role.ifPresent(user::setRole);
        Optional<User> userByUsername = userRepo.findByUsername(user.getUsername());
        if (userByUsername.isPresent()) {
            throw new NameAlreadyTakenException("This username is already taken!");
        }
        userRepo.save(user);
        String jwtToken=jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        User user=userRepo.findByEmail(request.getEmail())
                .orElseThrow(()->new NotFoundException("User not found"));

        String jwtToken=jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;
    }
}
