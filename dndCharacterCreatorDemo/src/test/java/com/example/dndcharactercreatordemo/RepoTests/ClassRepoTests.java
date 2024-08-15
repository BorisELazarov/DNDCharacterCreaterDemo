package com.example.dndcharactercreatordemo.RepoTests;

import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.repos.ClassRepo;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClassRepoTests {
    private final ClassRepo classRepo;

    @Autowired
    public ClassRepoTests(ClassRepo classRepo) {
        this.classRepo = classRepo;
    }

    @BeforeAll
    static void seedClasses(@Autowired ClassRepo seedRepo){
        Set<DNDclass> dnDclasses=new LinkedHashSet<>();
        dnDclasses.add(getDNDclass("Fighter", "Fights", HitDiceEnum.D10, true));
        dnDclasses.add(getDNDclass("Wizard", "Magic", HitDiceEnum.D6, false));
        dnDclasses.add(getDNDclass("Rogue", "Sneak", HitDiceEnum.D8, true));
        dnDclasses.add(getDNDclass("Barbarian", "Tank", HitDiceEnum.D12, false));
        seedRepo.saveAll(dnDclasses);
    }

    static DNDclass getDNDclass(String name, String description, HitDiceEnum hitDiceEnum, boolean isDeleted){
        DNDclass dnDclass = new DNDclass();
        dnDclass.setName(name);
        dnDclass.setHitDice(hitDiceEnum);
        dnDclass.setIsDeleted(isDeleted);
        dnDclass.setDescription(description);
        return dnDclass;
    }

    @Test
    void existsByNameReturnTrue(){
        Optional<DNDclass> dnDclass=classRepo.findAll().stream().findFirst();
        dnDclass.ifPresent(dclass -> assertTrue(classRepo.existsByName(dclass.getName())));
    }

    @Test
    void findByNameAreEquals(){
        Optional<DNDclass> dnDclass=classRepo.findAll().stream()
                .filter(x->!x.getIsDeleted()).findFirst();
        assertTrue(dnDclass.isPresent());
        Optional<DNDclass> foundCLass=classRepo.findByName(dnDclass.get().getName());
        assertTrue(foundCLass.isPresent());
        assertEquals(dnDclass,foundCLass);
    }

    @AfterAll
    static void rootClasses(@Autowired ClassRepo rootRepo){
        rootRepo.deleteAll();

    }
}
