package com.example.dndCharacterCreatorDemo.configs;

import com.example.dndCharacterCreatorDemo.entities.User;
import com.example.dndCharacterCreatorDemo.repos.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepo userRepo){
        return args -> {
            if (!userRepo.findAll()
                    .stream()
                    .filter(x->x.getUsername().equals("Boris"))
                    .findFirst().isPresent()) {
                User user = new User(
                        "Boris",
                        "BPass"
                );
                userRepo.save(user);
            }
        };
    }
}
