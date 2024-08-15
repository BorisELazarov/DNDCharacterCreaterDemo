package com.example.dndcharactercreatordemo.RepoTests;

import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.repos.CharacterRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    @Test
    void findByNameAreEquals(){
        Optional<Character> character=characterRepo.findAll().stream().filter(x-> !x.getIsDeleted()).findFirst();
        if (character.isPresent()) {
            Optional<Character> foundCharacter= characterRepo.findByName(character.get().getName());
            assertTrue(foundCharacter.isPresent());
            assertEquals(character,foundCharacter);
        }
    }
}
