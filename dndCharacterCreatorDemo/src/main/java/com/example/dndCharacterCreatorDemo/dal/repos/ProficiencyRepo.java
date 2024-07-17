package com.example.dndCharacterCreatorDemo.dal.repos;

import com.example.dndCharacterCreatorDemo.dal.entities.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProficiencyRepo extends JpaRepository<Proficiency, Long> {
}
