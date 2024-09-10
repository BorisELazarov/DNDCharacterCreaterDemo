package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.UserService;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> email,
            @RequestParam Optional<String> roleTitle,
            @RequestParam Optional<String> sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    )
    {
        return new ResponseEntity<>(
                userService.getUsers(name,email,roleTitle,sortBy,ascending),
                HttpStatus.OK
        );
    }

    @GetMapping(path="/login/{email}/{password}")
    public ResponseEntity<UserDTO> login(@PathVariable("email") String email,
                                         @PathVariable("password") String password){
        return new ResponseEntity<>(
                userService.getUser(email,password),
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

    @PutMapping(path = "changeEmail/{id}/{email}")
    public ResponseEntity<Void> changeEmail(
            @PathVariable("id") Long id,
            @PathVariable("email") String email
    ){
        userService.changeEmail(id,email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "changeRole/{id}/{role}")
    public ResponseEntity<Void> changeRole(
            @PathVariable("id") Long id,
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
