package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.auth.AuthenticationRequest;
import com.example.dndcharactercreatordemo.bll.dtos.auth.AuthenticationResponse;
import com.example.dndcharactercreatordemo.bll.dtos.auth.RegisterRequest;
import com.example.dndcharactercreatordemo.api.auth.config.JwtService;
import com.example.dndcharactercreatordemo.bll.dtos.users.LoginDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.UserMapper;
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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder,
                           JwtService jwtService, AuthenticationManager authManager,
                           UserMapper userMapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userMapper=userMapper;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user=new User();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        Optional<Role> role=roleRepo.findByTitle("user");
        role.ifPresent(user::setRole);
        Optional<User> userByUsername = userRepo.findByUsername(user.getUsername());
        if (userByUsername.isPresent()) {
            throw new NameAlreadyTakenException("This username is already taken!");
        }
        userRepo.save(user);
        LoginDTO login=new LoginDTO();
        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());
        String jwtToken=jwtService.generateToken(login);
        return new AuthenticationResponse(jwtToken,this.userMapper.toDto(user));
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                )
        );
        User user=userRepo.findByEmail(request.email())
                .orElseThrow(()->new NotFoundException("User not found"));

        LoginDTO login=new LoginDTO();
        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());

        String jwtToken=jwtService.generateToken(login);
        return new AuthenticationResponse(jwtToken,this.userMapper.toDto(user));
    }
}
