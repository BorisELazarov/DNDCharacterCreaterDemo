package com.example.dndCharacterCreatorDemo.dal.configs;

import com.example.dndCharacterCreatorDemo.dal.entities.Role;
import com.example.dndCharacterCreatorDemo.dal.entities.User;
import com.example.dndCharacterCreatorDemo.dal.repos.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner seedUser(UserRepo userRepo){
        return args -> {
            if (!userRepo.findAll()
                    .stream()
                    .filter(x->x.getUsername().equals("Boris"))
                    .findFirst().isPresent()) {
                User user = new User("Boris","BPass");
                Role role=new Role();
                role.setTitle("admin");
                user.setRole(role);
                userRepo.save(user);
            }
        };
    }
}
