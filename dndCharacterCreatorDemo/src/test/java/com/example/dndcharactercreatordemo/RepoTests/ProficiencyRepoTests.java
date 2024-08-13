package com.example.dndcharactercreatordemo.RepoTests;

import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProficiencyRepoTests {
    @Autowired
    ProficiencyRepo proficiencyRepo;

    @Test
    void getByName(){
        String name="Common";
        Proficiency proficiency= proficiencyRepo.findByName(name);
        assertTrue(proficiency.getName().equals(name) && !proficiency.getIsDeleted());
    }
}
