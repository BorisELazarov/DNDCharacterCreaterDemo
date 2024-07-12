package com.example.dndCharacterCreatorDemo.controllers;

import com.example.dndCharacterCreatorDemo.entities.User;
import com.example.dndCharacterCreatorDemo.services.UserService;
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
    public List<User> getUsers()
    {
        return userService.getUsers();
    }

    @GetMapping(path="{userId}")
    public User getUser(@PathVariable("userId") Long id){
        return userService.getUser(id);
    }

    @PostMapping
    public void registerUser(@RequestBody User user){
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
        userService.deleteUser(id);
    }
}
