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
import com.example.dndcharactercreatordemo.exceptions.customs.WrongPasswordException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final String NOT_FOUND_MESSAGE="The user is not found!";
    private static final String USERNAME_TAKEN_MESSAGE="This username is already taken!";
    private final UserRepo userRepo;
    private final UserMapper mapper;
    private final RoleRepo roleRepo;

    public UserServiceImpl(@NotNull UserRepo userRepo, @NotNull RoleRepo roleRepo,
                           @NotNull UserMapper mapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.mapper = mapper;
    }

    @PostConstruct
    private void seedDataForUsersAndRoles() {
        seedRoles();
        seedUser();
    }

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
    public List<UserDTO> getUsers() {
        return mapper.toDTOs(userRepo.findAll());
    }

    @Override
    public void addUser(UserDTO userDTO) {
        Optional<User> userByUsername = userRepo.findByUsername(userDTO.username());
        if (userByUsername.isPresent()) {
            throw new NameAlreadyTakenException(USERNAME_TAKEN_MESSAGE);
        }
        Optional<Role> role= roleRepo.findByTitle(userDTO.role());
        User user = mapper.fromDto(userDTO, role);
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void changeUsername(Long id, String username) {
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
    }

    @Override
    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();

        if (foundUser.getPassword().equals(oldPassword))
            throw new WrongPasswordException("Wrong password");

        if (newPassword != null &&
                newPassword.length() > 0 &&
                !newPassword.equals(oldPassword)) {
            foundUser.setPassword(newPassword);
        }
    }

    @Override
    public void softDeleteUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            foundUser.setIsDeleted(true);
            userRepo.save(foundUser);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

    }

    @Override
    public void hardDeleteUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();

            if (foundUser.getIsDeleted()) {
                userRepo.delete(foundUser);
            } else {
               throw new NotSoftDeletedException("The user must be soft deleted first!");
            }

        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

    }

    @Override
    public void restoreUser(String username, String password) {

    }

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return mapper.toDto(optionalUser.get());
    }

    @Override
    public UserDTO getUser(String username, String password) {
        Optional<User> optionalUser = userRepo.findByUsername(password);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User user= optionalUser.get();
        if (!user.getPassword().equals(password))
            throw new WrongPasswordException("Wrong password!");
        return mapper.toDto(optionalUser.get());
    }
}
