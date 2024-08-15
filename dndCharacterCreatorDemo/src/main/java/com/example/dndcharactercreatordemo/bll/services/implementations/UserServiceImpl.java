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

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final IMapper<UserDTO, User> mapper;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, IMapper<UserDTO, User> mapper) {
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
            throw new IllegalArgumentException("Error: there is already user with such name");
        }
        User user = mapper.fromDto(userDTO);
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, String username, String password) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            userNotFound();
        }
        User foundUser = optionalUser.get();
        if (username != null &&
                username.length() > 0 &&
                !username.equals(foundUser.getUsername())) {
            Optional<User> userByUsername = userRepo.findByUsername(username);
            if (userByUsername.isPresent()) {
                throw new IllegalArgumentException("Error: there is already user with such name");
            }
            foundUser.setUsername(username);
        }
        if (password != null &&
                password.length() > 0 &&
                !password.equals(foundUser.getPassword())) {
            foundUser.setPassword(password);
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
            userNotFound();
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
                throw new IllegalArgumentException("The user must be soft deleted before being hard deleted");
            }

        } else {
            userNotFound();
        }

    }

    private void userNotFound() {
        throw new IllegalArgumentException("User not found!");
    }

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            userNotFound();
        }
        return mapper.toDto(optionalUser.get());
    }
}
