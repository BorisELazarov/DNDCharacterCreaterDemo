package com.example.dndCharacterCreatorDemo.dal.configs;

import com.example.dndCharacterCreatorDemo.dal.entities.Proficiency;
import com.example.dndCharacterCreatorDemo.dal.entities.User;
import com.example.dndCharacterCreatorDemo.dal.repos.ProficiencyRepo;
import com.example.dndCharacterCreatorDemo.dal.repos.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProficiencyConfig {
    @Bean
    CommandLineRunner seedLanguageProficiencies(ProficiencyRepo proficiencyRepo) {
        Proficiency proficiency = new Proficiency("Common", "Language");
        List<Proficiency> proficiencies = new ArrayList<Proficiency>();
        proficiencies.add(proficiency);
        proficiency = new Proficiency("Elven", "Language");
        proficiencies.add(proficiency);
        proficiency = new Proficiency("Dwarven", "Language");
        proficiencies.add(proficiency);
        proficiency = new Proficiency("Orcish", "Language");
        proficiencies.add(proficiency);
        proficiency = new Proficiency("Celestial", "Language");
        proficiencies.add(proficiency);
        proficiency = new Proficiency("Infernal", "Language");
        proficiencies.add(proficiency);
        return args -> {
            for (Proficiency item : proficiencies) {
                if (proficiencyRepo.findAll().stream()
                        .noneMatch(x->x.getName().equals(item.getName()))
                ){
                    proficiencyRepo.save(item);
                }
            }
        };
    }
}
