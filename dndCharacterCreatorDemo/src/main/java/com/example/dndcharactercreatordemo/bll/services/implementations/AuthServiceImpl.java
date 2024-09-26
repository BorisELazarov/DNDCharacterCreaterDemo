package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.auth.AuthenticationRequest;
import com.example.dndcharactercreatordemo.bll.dtos.auth.AuthenticationResponse;
import com.example.dndcharactercreatordemo.api.auth.config.JwtService;
import com.example.dndcharactercreatordemo.bll.dtos.users.LoginCredentials;
import com.example.dndcharactercreatordemo.bll.dtos.users.RegisterDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.UserMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.AuthService;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.EmailAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.WrongPasswordException;
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
    public AuthenticationResponse register(RegisterDTO registerDTO) {
        if (userRepo.findByEmail(registerDTO.email()).isPresent()){
            throw new EmailAlreadyTakenException("This email is already taken!");
        }
        if (userRepo.findByUsername(registerDTO.username()).isPresent()) {
            throw new NameAlreadyTakenException("This username is already taken!");
        }

        User user= userMapper.fromDto(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> role=roleRepo.findByTitle("user");
        role.ifPresent(user::setRole);
        user = userRepo.save(user);
        LoginCredentials login=new LoginCredentials();
        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());
        String jwtToken=jwtService.generateToken(login);
        return new AuthenticationResponse(jwtToken,this.userMapper.toDto(user));
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepo.findByEmail(request.email())
                .orElseThrow(()->new NotFoundException("There is no user with such email!"));

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                )
        );

        LoginCredentials login=new LoginCredentials();
        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());

        String jwtToken=jwtService.generateToken(login);
        return new AuthenticationResponse(jwtToken,this.userMapper.toDto(user));
    }
}
