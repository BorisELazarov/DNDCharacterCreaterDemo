package com.example.dndCharacterCreatorDemo.bll.services;

import com.example.dndCharacterCreatorDemo.bll.dtos.UserDTO;
import com.example.dndCharacterCreatorDemo.bll.mappers.IMapper;
import com.example.dndCharacterCreatorDemo.bll.mappers.UserMapper;
import com.example.dndCharacterCreatorDemo.dal.entities.Role;
import com.example.dndCharacterCreatorDemo.dal.entities.User;
import com.example.dndCharacterCreatorDemo.dal.repos.RoleRepo;
import com.example.dndCharacterCreatorDemo.dal.repos.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final IMapper<UserDTO,User> mapper;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo=userRepo;
        this.roleRepo=roleRepo;
        this.mapper=new UserMapper();
    }

    public List<UserDTO> getUsers() {
        List<User> users=userRepo.findAll();
        List<UserDTO> userDTOS=new ArrayList<>();
        for (User user: users){
            userDTOS.add(mapper.toDto(user));
        }
        return userDTOS;
    }

    public void addUser(UserDTO userDTO) {
        Optional<User> userByUsername=userRepo.findAll()
                .stream()
                .filter(x->x.getUsername().equals(userDTO.getUsername()) && !x.getIsDeleted())
                .findFirst();
        if (userByUsername.isPresent()) {
            throw new IllegalArgumentException("Error: there is already user with such name");
        }
        User user=mapper.fromDto(userDTO);
        //roleRepo.save(user.getRole());
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
                    .filter(x->x.getUsername().equals(username) && !x.getIsDeleted())
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
        foundUser.setIsDeleted(true);
        userRepo.save(foundUser);
    }

    private void UserNotFound(){
        throw new IllegalArgumentException("User not found!");
    }

    public UserDTO getUser(Long id) {
        Optional<User> user=userRepo.findById(id);
        if (!user.isPresent()) {
            UserNotFound();
        }
        return mapper.toDto(user.get());
    }
}
