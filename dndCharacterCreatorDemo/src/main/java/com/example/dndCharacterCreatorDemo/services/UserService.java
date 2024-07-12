package com.example.dndCharacterCreatorDemo.services;

import com.example.dndCharacterCreatorDemo.entities.User;
import com.example.dndCharacterCreatorDemo.repos.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo=userRepo;
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public void addUser(User user) {
        Optional<User> userByUsername=userRepo.findAll()
                .stream()
                .filter(x->x.getUsername().equals(user.getUsername()))
                .findFirst();
        if (userByUsername.isPresent()) {
            throw new IllegalArgumentException("Error: there is already user with such name");
        }
        userRepo.save(user);
    }

    @Transactional
    public void updateUser(Long id, String username, String password) {
        Optional<User> user=userRepo.findById(id);
        if (!user.isPresent()) {
            UserNotFound();
        }
        User foundUser=user.get();
        if (username!=null &&
                username.length()>0 &&
                !username.equals(foundUser.getUsername())) {
            Optional<User> userByUsername=userRepo.findAll()
                    .stream()
                    .filter(x->x.getUsername().equals(username))
                    .findFirst();
            if(userByUsername.isPresent())
            {
                throw new IllegalArgumentException("Error: there is already user with such name");
            }
            foundUser.setUsername(username);
        }
        if (password!=null &&
                password.length()>0 &&
                !password.equals(foundUser.getPassword())) {
            foundUser.setPassword(password);
        }
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()){
            UserNotFound();
        }
        User foundUser=user.get();
        foundUser.setDeleted(true);
        userRepo.save(foundUser);
    }

    private void UserNotFound(){
        throw new IllegalArgumentException("User not found!");
    }

    public User getUser(Long id) {
        Optional<User> user=userRepo.findById(id);
        if (!user.isPresent()) {
            UserNotFound();
        }
        return user.get();
    }
}
