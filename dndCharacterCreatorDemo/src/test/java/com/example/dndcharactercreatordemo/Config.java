package com.example.dndcharactercreatordemo;

import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public ProficiencyRepo proficiencyRepo(ProficiencyRepo proficiencyRepo){
        return proficiencyRepo;
    }
}
