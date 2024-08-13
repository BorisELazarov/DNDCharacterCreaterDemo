package com.example.dndcharactercreatordemo.dal.repos;

import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProficiencyRepo extends JpaRepository<Proficiency, Long> {
    @Query("Select p from Proficiency p where p.name=:name and p.isDeleted=false")
    public Proficiency findByName(String name);
}
