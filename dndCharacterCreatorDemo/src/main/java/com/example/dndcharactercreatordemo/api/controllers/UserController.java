package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUndeletedUsers()
    {
        return new ResponseEntity<>(
                userService.getUsers(),
                HttpStatus.OK
        );
    }

    @GetMapping(path="/login/{username}/{password}")
    public ResponseEntity<UserDTO> login(@PathVariable("username") String username,
                                         @PathVariable("password") String password){
        return new ResponseEntity<>(
                userService.getUser(username,password),
                HttpStatus.OK
        );
    }

    @PutMapping(path="/restore/{username}/{password}")
    public ResponseEntity<Void> restoreAccount(@PathVariable("username") String username,
                                         @PathVariable("password") String password){
        userService.restoreUser(username,password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long id){
        return new ResponseEntity<>(
                userService.getUser(id),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO user){
        return new ResponseEntity<>(
                userService.addUser(user),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path="changeUsername/{userId}/{username}")
    public ResponseEntity<Void> changeUsername(
            @PathVariable("userId") Long id,
            @PathVariable("username") String username){
        userService.changeUsername(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="changePassword/{userId}/{oldPassword}/{newPassword}")
    public ResponseEntity<Void> changePassword(
            @PathVariable("userId") Long id,
            @PathVariable("oldPassword") String oldPassword,
            @PathVariable("newPassword") String newPassword){
        userService.changePassword(id,oldPassword,newPassword);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "changeEmail/{userid}/{email}")
    public ResponseEntity<Void> changeEmail(
            @PathVariable("userId") Long id,
            @PathVariable("email") String email
    ){
        userService.changeEmail(id,email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "changeRole/{userid}/{role}")
    public ResponseEntity<Void> changeRole(
            @PathVariable("userId") Long id,
            @PathVariable("role") String role
    ){
        userService.changeRole(id,role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long id) {
         userService.softDeleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/confirmed/{userId}")
    public ResponseEntity<Void> hardDeleteUser(@PathVariable("userId") Long id){
        userService.hardDeleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
