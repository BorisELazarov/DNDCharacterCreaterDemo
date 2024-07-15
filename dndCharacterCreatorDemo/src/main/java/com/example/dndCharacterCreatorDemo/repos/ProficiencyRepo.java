package com.example.dndCharacterCreatorDemo.repos;

import com.example.dndCharacterCreatorDemo.entities.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProficiencyRepo extends JpaRepository<Proficiency, Long> {
}
