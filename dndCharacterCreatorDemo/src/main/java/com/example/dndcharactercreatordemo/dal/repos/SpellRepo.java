package com.example.dndcharactercreatordemo.dal.repos;

import com.example.dndcharactercreatordemo.dal.entities.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpellRepo extends JpaRepository<Spell,Long> {
    @Query("Select s from Spell s where s.name=:name and s.isDeleted=false")
    Optional<Spell> findByName(String name);
}
