package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserDTO> getAllUsers()
    {
        return userService.getUsers();
    }

    @GetMapping(path="{userId}")
    public UserDTO getUser(@PathVariable("userId") Long id){
        return userService.getUser(id);
    }

    @PostMapping
    public void registerUser(@RequestBody UserDTO user){
        userService.addUser(user);
    }

    @PutMapping(path="{userId}")
    public void updateUser(
            @PathVariable("userId") Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password){
        userService.updateUser(id,username,password);
    }

    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") Long id) {
        userService.softDeleteUser(id);
    }
}
