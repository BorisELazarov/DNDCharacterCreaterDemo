package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.UserMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.UserService;
import com.example.dndcharactercreatordemo.dal.entities.Privilege;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final String NOT_FOUND_MESSAGE="The user is not found!";
    private static final String USERNAME_TAKEN_MESSAGE="This username is already taken!";
    private final UserRepo userRepo;
    private final UserMapper mapper;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, UserMapper mapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.mapper = mapper;
    }

    @PostConstruct
    private void seedDataForUsersAndRoles() {
        seedRoles();
        seedUser();
    }

    //to be optimised
    private void seedRoles() {
        if (roleRepo.count()>0)
            return;
        List<Role> roles = new ArrayList<>();
        Set<Privilege> privileges=new LinkedHashSet<>();

        privileges.add(getPrivilege("manage profile"));
        privileges.add(getPrivilege("manage characters"));
        roles.add(getRole("user",privileges));

        privileges.stream().skip(1).findFirst().orElse(new Privilege()).setTitle("manage data");
        roles.add(getRole("data manager",privileges));

        privileges.stream().skip(1).findFirst().orElse(new Privilege()).setTitle("manage users");
        roles.add(getRole("admin",privileges));

        roleRepo.saveAll(roles);
    }

    static Role getRole(String title, Set<Privilege> privileges){
        Role role=new Role();
        role.setTitle(title);
        role.setPrivileges(privileges);
        return role;
    }

    static Privilege getPrivilege(String title) {
        Privilege privilege = new Privilege();
        privilege.setTitle(title);
        return privilege;
    }

    private void seedUser() {
        if (userRepo.findByUsername("Boris").isEmpty()) {
            User user = new User();
            user.setUsername("Boris");
            user.setPassword("BPass");
            Optional<Role> role = roleRepo.findByTitle("admin");
            role.ifPresent(user::setRole);
            userRepo.save(user);
        }
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(
                mapper.toDTOs(userRepo.findAll()),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> addUser(UserDTO userDTO) {
        Optional<User> userByUsername = userRepo.findByUsername(userDTO.username());
        if (userByUsername.isPresent()) {
            throw new NameAlreadyTakenException(USERNAME_TAKEN_MESSAGE);
        }
        Optional<Role> role= roleRepo.findByTitle(userDTO.role());
        User user = mapper.fromDto(userDTO, role);
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> updateUser(Long id, String username, String password) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();
        if (username != null &&
                username.length() > 0 &&
                !username.equals(foundUser.getUsername())) {
            Optional<User> userByUsername = userRepo.findByUsername(username);
            if (userByUsername.isPresent()) {
                throw new NameAlreadyTakenException(USERNAME_TAKEN_MESSAGE);
            }
            foundUser.setUsername(username);
        }
        if (password != null &&
                password.length() > 0 &&
                !password.equals(foundUser.getPassword())) {
            foundUser.setPassword(password);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> softDeleteUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            foundUser.setIsDeleted(true);
            userRepo.save(foundUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

    }

    @Override
    public ResponseEntity<Void> hardDeleteUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();

            if (foundUser.getIsDeleted()) {userRepo.delete(foundUser);
               return new ResponseEntity<>(HttpStatus.OK);
            } else {
               throw new NotSoftDeletedException("The user must be soft deleted first!");
            }

        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

    }

    @Override
    public ResponseEntity<UserDTO> getUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return new ResponseEntity<>(
                mapper.toDto(optionalUser.get()),
                HttpStatus.OK
        );
    }
}
