package com.example.dndcharactercreatordemo.RepoTests;

import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.CharacterRepo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CharacterRepoTests {
    private final CharacterRepo characterRepo;

    @Autowired
    public CharacterRepoTests(CharacterRepo characterRepo) {
        this.characterRepo = characterRepo;
    }

    @BeforeAll
    static void seedData(@Autowired CharacterRepo seedRepo){
        List<Character> characters=new LinkedList<>();
        characters.add(getCharacter(true, "Norman", (byte)20,
                (byte)20, (byte)20, (byte) 20,
                (byte) 20, (byte) 20, (byte) 20));
        characters.add(getCharacter(false, "Morgan", (byte)2,
                (byte)12, (byte)16, (byte) 10,
                (byte) 14, (byte) 8, (byte) 14));
        characters.add(getCharacter(false, "Jordan", (byte)3,
                (byte) 14, (byte) 8, (byte) 14,
                (byte)12, (byte)16, (byte) 10));
        seedRepo.saveAll(characters);
    }

    static Character getCharacter(boolean isDeleted, String name,
                                  byte level, byte baseStr,
                                  byte baseDex, byte baseCon,
                                  byte baseInt, byte baseWis,
                                  byte baseCha){
        Character character=new Character();
        character.setIsDeleted(isDeleted);
        character.setName(name);
        character.setLevel(level);
        character.setBaseStr(baseStr);
        character.setBaseDex(baseDex);
        character.setBaseCon(baseCon);
        character.setBaseInt(baseInt);
        character.setBaseWis(baseWis);
        character.setBaseCha(baseCha);
        return character;
    }

    @Test
    void findByNameAreEquals(){
        Optional<Character> character=characterRepo.findAll().stream().filter(x-> !x.getIsDeleted()).findFirst();
        if (character.isPresent()) {
            Optional<Character> foundCharacter= characterRepo.findByName(character.get().getName());
            assertTrue(foundCharacter.isPresent());
            assertEquals(character,foundCharacter);
        }
    }

    @AfterAll
    static void rootData(@Autowired CharacterRepo rootRepo){
        rootRepo.deleteAll();
    }
}
