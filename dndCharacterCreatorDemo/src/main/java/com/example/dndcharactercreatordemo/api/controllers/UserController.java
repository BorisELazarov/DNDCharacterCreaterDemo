package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        return userService.getUsers();
    }

    @GetMapping(path="{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long id){
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO user){
        return userService.addUser(user);
    }

    @PutMapping(path="{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("userId") Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password){
        return userService.updateUser(id,username,password);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
         return userService.softDeleteUser(id);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteUser(@RequestParam Long id){
        return userService.hardDeleteUser(id);
    }
}
