package com.example.dndcharactercreatordemo.bll.services;

import com.example.dndcharactercreatordemo.bll.dtos.UserDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.bll.mappers.UserMapper;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final IMapper<UserDTO,User> mapper;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo=userRepo;
        this.mapper=new UserMapper(roleRepo);
    }

    public List<UserDTO> getUsers() {
        return mapper.toDtos(userRepo.findAll());
    }

    public void addUser(UserDTO userDTO) {
        User userByUsername=userRepo.findByUsername(userDTO.getUsername());
        if (userByUsername!=null && !userByUsername.getIsDeleted()) {
            throw new IllegalArgumentException("Error: there is already user with such name");
        }
        User user=mapper.fromDto(userDTO);
        userRepo.save(user);
    }

    @Transactional
    public void updateUser(Long id, String username, String password) {
        Optional<User> optionalUser=userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            userNotFound();
        }
        User foundUser=optionalUser.get();
        if (username!=null &&
                username.length()>0 &&
                !username.equals(foundUser.getUsername())) {
            User userByUsername=userRepo.findByUsername(foundUser.getUsername());
            if(userByUsername==null)
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
        Optional<User> optionalUser=userRepo.findById(id);
        if (optionalUser.isPresent()){
            User foundUser=optionalUser.get();
            foundUser.setIsDeleted(true);
            userRepo.save(foundUser);
        }
        else {
            userNotFound();
        }

    }

    private void userNotFound(){
        throw new IllegalArgumentException("User not found!");
    }

    public UserDTO getUser(Long id) {
        Optional<User> optionalUser=userRepo.findById(id);
        if (optionalUser.isEmpty()){
            userNotFound();
        }
        return mapper.toDto(optionalUser.get());
    }
}
