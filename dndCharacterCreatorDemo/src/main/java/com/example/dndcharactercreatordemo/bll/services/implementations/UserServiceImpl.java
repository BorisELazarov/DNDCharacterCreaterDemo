package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.UserService;
import com.example.dndcharactercreatordemo.dal.entities.Privilege;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final IMapper<UserDTO, User> mapper;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, IMapper<UserDTO, User> mapper) {
        this.userRepo=userRepo;
        this.roleRepo=roleRepo;
        this.mapper=mapper;
    }

    @PostConstruct
    private void seedDataForUsersAndRoles(){
        seedRoles();
        seedUser();
    }

    //to be optimised
    private void seedRoles() {
        if (roleRepo.count()<1) {
            Privilege manageProfile = new Privilege();
            manageProfile.setTitle("manage profile");
            Privilege manageUsers = new Privilege();
            manageUsers.setTitle("manage users");
            Privilege manageCharacters = new Privilege();
            manageCharacters.setTitle("manage characters");
            Privilege manageData = new Privilege();
            manageData.setTitle("manage data");
            Role admin = new Role();
            admin.setTitle("admin");
            admin.setPrivileges(new LinkedHashSet<>());
            admin.getPrivileges().add(manageProfile);
            admin.getPrivileges().add(manageUsers);
            Role dataManager = new Role();
            dataManager.setTitle("data manager");
            dataManager.setPrivileges(new LinkedHashSet<>());
            dataManager.getPrivileges().add(manageProfile);
            dataManager.getPrivileges().add(manageData);
            Role user = new Role();
            user.setTitle("user");
            user.setPrivileges(new LinkedHashSet<>());
            user.getPrivileges().add(manageProfile);
            user.getPrivileges().add(manageCharacters);
            List<Role> roles=new ArrayList<>();
            roles.add(user);
            roles.add(dataManager);
            roles.add(admin);
            roleRepo.saveAll(roles);
        }
    }

    private void seedUser(){
        if (userRepo.findByUsername("Boris")==null) {
            User user = new User();
            user.setUsername("Boris");
            user.setPassword("BPass");
            Role role= roleRepo.findByTitle("admin");
            if (role!=null)
                user.setRole(role);
            userRepo.save(user);
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return mapper.toDTOs(userRepo.findAll());
    }

    @Override
    public void addUser(UserDTO userDTO) {
        User userByUsername=userRepo.findByUsername(userDTO.username());
        if (userByUsername!=null) {
            throw new IllegalArgumentException("Error: there is already user with such name");
        }
        User user=mapper.fromDto(userDTO);
        userRepo.save(user);
    }

    @Override
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

    @Override
    public void softDeleteUser(Long id) {
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

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> optionalUser=userRepo.findById(id);
        if (optionalUser.isEmpty()){
            userNotFound();
        }
        return mapper.toDto(optionalUser.get());
    }
}
