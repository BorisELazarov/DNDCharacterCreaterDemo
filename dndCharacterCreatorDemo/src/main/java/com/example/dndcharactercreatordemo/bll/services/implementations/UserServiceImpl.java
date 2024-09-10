package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.UserMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.UserService;
import com.example.dndcharactercreatordemo.dal.entities.Privilege;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
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
    @PersistenceContext
    private EntityManager em;
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
            user.setPassword(String.valueOf("BPass".hashCode()));
            user.setEmail("email@abv.bg");
            Optional<Role> role = roleRepo.findByTitle("admin");
            role.ifPresent(user::setRole);
            userRepo.save(user);
        }
    }

    @Override
    public List<UserDTO> getUsers(Optional<String> username,
                                  Optional<String> email,
                                  Optional<String> roleTitle,
                                  Optional<String> sortBy,
                                  boolean ascending) {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery= cb.createQuery(User.class);
        Root<User> root= criteriaQuery.from(User.class);
        String usernameParam= username.orElse("");
        String roleTitleParam= roleTitle.orElse("");
        String emailParam= email.orElse("");
        Join<User,Role> joinRoles= root.join("role",JoinType.INNER);
        criteriaQuery.select(root)
                .where(cb.and(
                        cb.and(
                                cb.like(root.get("username"),"%"+usernameParam+"%"),
                        cb.like(joinRoles.get("title"),"%"+roleTitleParam+"%")
                        ),
                        cb.like(root.get("email"),"%"+emailParam+"%")
                ));
        if (ascending){
            criteriaQuery.orderBy(cb.asc(root.get(sortBy.orElse("id"))));
        }else {
            criteriaQuery.orderBy(cb.desc(root.get(sortBy.orElse("id"))));
        }
        Query query = em.createQuery(criteriaQuery);
        List<User> users=query.getResultList();
        return mapper.toDTOs(users);
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        if (userRepo.findByUsername(userDTO.username()).isPresent()) {
            throw new NameAlreadyTakenException(USERNAME_TAKEN_MESSAGE);
        }
        if (userRepo.findByEmail(userDTO.email()).isPresent()){
            throw new EmailAlreadyTakenException("This email is already taken!");
        }
        Optional<Role> role= roleRepo.findByTitle(userDTO.role());
        User user = mapper.fromDto(userDTO, role);
        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        return mapper.toDto(userRepo.save(user));
    }

    @Override
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
            userRepo.save(foundUser);
        }
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();
        oldPassword=String.valueOf(oldPassword.hashCode());

        if (!foundUser.getPassword().equals(oldPassword))
            throw new WrongPasswordException("Wrong password");
        if (newPassword != null &&
                newPassword.length() > 0) {
            foundUser.setPassword(String.valueOf(newPassword.hashCode()));
            userRepo.save(foundUser);
        }
    }

    @Override
    public void changeEmail(Long id, String email) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();
        if (userRepo.findByEmail(email).isPresent()){
            throw new EmailAlreadyTakenException("");
        }
        foundUser.setEmail(email);
        userRepo.save(foundUser);
    }

    @Override
    public void changeRole(Long id, String role) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();
        Optional<Role> foundRole=roleRepo.findByTitle(role);
        if (foundRole.isEmpty()){
            throw new NotFoundException("There is no such role!");
        }
        foundUser.setRole(foundRole.get());
        userRepo.save(foundUser);
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
        Optional<User> deletedUser=userRepo.findDeletedByUsernameAndPassword(username, password);
        if (deletedUser.isEmpty()){
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        if (userRepo.findByUsername(username).isPresent()){
            throw new NameAlreadyTakenException("There is already user with such username.");
        }
        User user= deletedUser.get();
        user.setIsDeleted(false);
        userRepo.save(user);
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
    public UserDTO getUser(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("There is no user with such email!");
        }
        User user= optionalUser.get();
        if (!user.getPassword().equals(String.valueOf(password.hashCode())))
            throw new WrongPasswordException("Wrong password!");
        return mapper.toDto(optionalUser.get());
    }
}
