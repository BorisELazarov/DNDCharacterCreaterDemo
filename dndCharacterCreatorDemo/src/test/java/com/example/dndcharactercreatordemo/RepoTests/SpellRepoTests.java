package com.example.dndcharactercreatordemo.RepoTests;

import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
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
class SpellRepoTests {
    private final SpellRepo spellRepo;

    @BeforeAll
    static void seedSpells(@Autowired SpellRepo seedRepo){
        Set<Spell> spells=new LinkedHashSet<>();
        spells.add(
          getSpell(
                  false, "Fire",
                  1, "A",
                  20, "asfd",
                  "V, M", 0,
                  "FIreee"
          )
        );
        spells.add(
                getSpell(
                        true, "Cold",
                        1, "A",
                        25, "ads",
                        "V, M", 0,
                        "Cold cold"
                )
        );
        spells.add(
                getSpell(
                        false, "Thunder",
                        1, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Thunder"
                )
        );
        seedRepo.saveAll(spells);
    }

    static Spell getSpell(Boolean isDeleted,
                          String name, int level,
                          String castingTime, int castingRange,
                          String target, String components,
                          int duration, String description){
        Spell spell=new Spell();
        spell.setIsDeleted(isDeleted);
        spell.setName(name);
        spell.setDescription(description);
        spell.setLevel(level);
        spell.setCastingRange(castingRange);
        spell.setTarget(target);
        spell.setComponents(components);
        spell.setDuration(duration);
        spell.setCastingTime(castingTime);
        return spell;
    }

    @Autowired
    public SpellRepoTests(SpellRepo spellRepo) {
        this.spellRepo = spellRepo;
    }

    @Test
    void findByNameAreEquals(){
        Optional<Spell> spell=spellRepo.findAll().stream()
                .filter(x->!x.getIsDeleted()).findFirst();
        assertTrue(spell.isPresent());
        Optional<Spell> foundSpell=spellRepo.findByName(spell.get().getName());
        assertTrue(foundSpell.isPresent());
        assertEquals(spell,foundSpell);
    }



    @AfterAll
    static void rootData(@Autowired SpellRepo rootRepo){
        rootRepo.deleteAll();
    }
}
