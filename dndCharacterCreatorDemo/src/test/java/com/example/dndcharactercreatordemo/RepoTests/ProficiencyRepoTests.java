package com.example.dndcharactercreatordemo.RepoTests;

import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProficiencyRepoTests {
    private final ProficiencyRepo proficiencyRepo;


    @Autowired
    public ProficiencyRepoTests(ProficiencyRepo proficiencyRepo) {
        this.proficiencyRepo = proficiencyRepo;
    }

    @BeforeAll
    static void seedData(@Autowired ProficiencyRepo seedRepo){
        String language="Language";
        List<Proficiency> proficiencies = new ArrayList<>();
        proficiencies.add(seedProficiency("Common",language));
        proficiencies.add(seedProficiency("Elven",language));
        proficiencies.add(seedProficiency("Dwarvish",language));
        proficiencies.add(seedProficiency("Orcish",language));
        proficiencies.add(seedProficiency("Celestial", language));
        proficiencies.add(seedProficiency("Infernal",language));
        seedRepo.saveAll(proficiencies);
    }

    private static Proficiency seedProficiency(String name, String type){
        Proficiency proficiency=new Proficiency();
        proficiency.setName(name);
        proficiency.setType(type);
        return proficiency;
    }


    @Test
    void findByNameAreEquals(){
        Optional<Proficiency> proficiency=proficiencyRepo.findAll().stream()
                .filter(x->!x.getIsDeleted()).findFirst();
        assertTrue(proficiency.isPresent());

        Optional<Proficiency> proficiencyByName=proficiencyRepo.findByName(proficiency.get().getName());
        assertTrue(proficiencyByName.isPresent());
        assertEquals(proficiency,proficiencyByName);
    }

    @AfterAll
    static void rootData(@Autowired ProficiencyRepo rootRepo){
        rootRepo.deleteAll();
    }
}
